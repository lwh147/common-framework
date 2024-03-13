package com.lwh147.common.mybatisplus.idgenerator;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lwh147.common.core.exception.CommonExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 集群模式下雪花算法ID生成器，各工作机器竞争抢占注册自己的工作机器ID和所属数据中心ID
 * 策略详情如下：
 * <p>
 * 1.应用启动后注入当前Bean时，在PostCounstruct阶段对雪花算法对象的工作机器ID和所属数据中心ID信息进行初始化
 * <p>
 * 2.首先去Redis中获取上一次被注册的节点 {@code worker} (相比从头开始效率高一些)
 * <p>
 * 3.调用 {@link Worker#next()} 方法获取下一个节点并尝试抢占（使用Redisson对当前节点加锁，加锁成功即视为抢占成功）
 * <p>
 * 4.如果抢占成功，使用抢占到的工作节点信息创建雪花算法对象并更新Redis上一次被注册的节点为当前节点；如果抢占失败，重复步骤3
 * <p>
 * 5.当步骤3重复次数达到最大尝试次数时说明所有节点均被注册，启动失败，需要人工介入调整
 *
 * @author lwh
 * @date 2021/11/26 17:16
 **/
@Slf4j
public class ClusterIdGenerator implements IdentifierGenerator {
    /**
     * 最大尝试次数，节点数量上限
     **/
    public static final int MAX_TRY_COUNT = 32 * 32;
    /**
     * 雪花算法缓存Key前缀
     **/
    public static final String CACHE_KEY_PREFIX = "snowflake:";
    /**
     * 当前最大工作节点缓存Key
     **/
    public static final String TARGET_WORKER_CACHE_KEY = CACHE_KEY_PREFIX + "target-worker";
    /**
     * 已被抢占的节点的Key的锁前缀
     **/
    public static final String LOCKED_WORKER_CACHE_KEY_PREFIX = CACHE_KEY_PREFIX + "locked-worker";

    /**
     * 雪花算法对象
     **/
    private Snowflake snowflake;
    /**
     * 抢占成功时的锁对象，上锁成功后应用在运行期间锁会自动续约，默认永不解锁，应用停止后经过一定时间（默认30s）会自动解锁
     **/
    private RLock lock;

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 初始化雪花算法对象
     **/
    @PostConstruct
    private void init() {
        if (this.snowflake != null) {
            // 已经初始化，无需操作
            return;
        }

        // 从Redis中获取上一次被注册的节点
        Worker worker = (Worker) redisTemplate.opsForValue().get(TARGET_WORKER_CACHE_KEY);
        log.debug("从Redis中获取到的上一次被注册的节点[{}]", worker);
        if (worker == null) {
            // 没有被注册的节点
            worker = new Worker();
        } else {
            // 从下一个节点开始
            worker.next();
        }

        // 记录重试次数
        int tryCount = 0;
        while (tryCount < MAX_TRY_COUNT) {
            // 尝试抢占注册节点
            if (this.tryLock(worker)) {
                // 生成雪花算法对象
                this.snowflake = new Snowflake(worker.getWorkerId(), worker.getDataCenterId());
                // 更新Redis中上一次被注册的节点信息
                redisTemplate.opsForValue().set(TARGET_WORKER_CACHE_KEY, worker);
                log.info("成功初始化雪花算法工作节点为[{}]", worker.toString());
                return;
            }
            // 尝试抢占下一个节点
            worker.next();
            tryCount++;
        }

        // 节点已满，初始化失败
        throw CommonExceptionEnum.COMMON_ERROR.toException("已达最大工作机器数，无法启动更多服务，如需启动请人工清除节点");
    }

    /**
     * 尝试为工作节点加锁
     *
     * @param worker 工作节点对象
     * @return 是否加锁成功
     **/
    private boolean tryLock(Worker worker) {
        this.lock = redissonClient.getLock(LOCKED_WORKER_CACHE_KEY_PREFIX + worker.generateCacheKey());
        if (this.lock.tryLock()) {
            log.debug("抢占工作节点[{}]成功", worker.toString());
            return true;
        } else {
            log.debug("抢占工作节点[{}]失败", worker.toString());
            return false;
        }
    }

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }

    public Long nextId() {
        return snowflake.nextId();
    }
}
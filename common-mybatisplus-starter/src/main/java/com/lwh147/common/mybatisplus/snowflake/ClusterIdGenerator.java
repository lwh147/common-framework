package com.lwh147.common.mybatisplus.snowflake;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lwh147.common.core.exception.CommonExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
 * <p>
 * 6.Bean销毁时在PreDestroy阶段对当前应用抢占的工作节点锁进行释放
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
     * 工作节点缓存Key
     **/
    public static final String WORKER_CACHE_KEY = CACHE_KEY_PREFIX + "worker";
    /**
     * 锁的Key
     **/
    public static final String LOCK_CACHE_KEY_PREFIX = CACHE_KEY_PREFIX + "locked";
    /**
     * 抢占成功时的锁对象
     **/
    private RLock lock;
    /**
     * 工作节点对象
     **/
    private Worker worker;
    /**
     * 雪花算法对象
     **/
    private Snowflake snowflake;

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }

    public Long nextId() {
        return snowflake.nextId();
    }

    /**
     * 初始化雪花算法对象
     **/
    @PostConstruct
    public void init() {
        if (this.snowflake != null) {
            // 已经初始化，无需操作
            return;
        }
        // 从Redis中获取上一次被注册的节点
        this.worker = this.getWorker();
        log.debug("从Redis中获取到的上一次被注册的节点[{}]", this.worker);
        if (this.worker == null) {
            // 没有被注册的节点
            this.worker = new Worker();
        } else {
            // 从下一个节点开始
            this.worker.next();
        }
        // 记录重试次数
        int tryCount = 0;
        while (tryCount < MAX_TRY_COUNT) {
            // 尝试抢占注册节点
            if (this.tryLock()) {
                // 生成雪花算法对象
                this.snowflake = new Snowflake(this.worker.getWorkerId(), this.worker.getDataCenterId());
                // 更新Redis中上一次被注册的节点信息
                this.setWorker(this.worker);
                log.info("初始化雪花算法工作节点为[{}]", this.worker.toString());
                return;
            }
            // 尝试抢占下一个节点
            this.worker.next();
            tryCount++;
        }
        // 节点已满，初始化失败
        throw CommonExceptionEnum.COMMON_ERROR.toException("已达最大工作机器数，无法启动更多服务，如需启动请人工清除节点");
    }

    /**
     * 应用停止时在当前Bean被销毁前释放抢占的工作节点
     **/
    @PreDestroy
    public void destory() {
        this.lock.unlock();
    }

    /**
     * 尝试为下一个工作节点加锁
     *
     * @return 是否加锁成功
     **/
    public Boolean tryLock() {
        // 生成锁
        RLock lock = redissonClient.getLock(LOCK_CACHE_KEY_PREFIX + this.worker.generateCacheKey());
        // 尝试抢占（加锁）
        if (lock.tryLock()) {
            this.lock = lock;
            log.debug("抢占工作节点[{}]成功", this.worker.toString());
            return true;
        } else {
            log.debug("抢占工作节点[{}]失败", this.worker.toString());
            return false;
        }
    }

    /**
     * 从Redis中获取上一次被注册的节点对象
     * <p>
     * 没有在缓存中获取到时，说明当前没有实例抢占节点资源，返回 {@code null}
     *
     * @return 获取到的Worker对象，没获取到返回 {@code null}
     **/
    public Worker getWorker() {
        // 从缓存中获取
        Object o = redisTemplate.opsForValue().get(WORKER_CACHE_KEY);
        if (o != null) {
            // 直接返回获取到的数据
            return (Worker) o;
        }
        // 没有节点
        return null;
    }

    /**
     * 更新节点
     *
     * @param worker 要更新的Worker对象
     **/
    public void setWorker(Worker worker) {
        redisTemplate.opsForValue().set(WORKER_CACHE_KEY, worker);
    }

}
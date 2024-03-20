package com.lwh147.common.mybatisplus.generator.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 工作节点类，封装了工作机器ID和其所属数据中心ID
 *
 * @author lwh
 * @date 2021/11/26 9:11
 **/
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Worker implements Serializable {
    /**
     * 最大工作机器ID
     **/
    public static final Long MAX_WORKER_ID = 31L;
    /**
     * 最大数据中心ID
     **/
    public static final Long MAX_DATA_CENTER_ID = 31L;
    /**
     * 默认机器ID
     **/
    public static final Long DEFAULT_WORKER_ID = 0L;
    /**
     * 默认数据中心ID
     **/
    public static final Long DEFAULT_DATA_CENTER_ID = 0L;

    /**
     * 工作机器ID，0-31
     **/
    private Long workerId = DEFAULT_WORKER_ID;
    /**
     * 工作机器属于的数据中心ID，0-31
     **/
    private Long dataCenterId = DEFAULT_DATA_CENTER_ID;

    /**
     * 下一个节点，到达最大节点时从初始节点开始重新循环生成
     **/
    public void next() {
        if (this.workerId < MAX_WORKER_ID) {
            this.workerId++;
        } else if (this.dataCenterId < MAX_DATA_CENTER_ID) {
            this.dataCenterId++;
            this.workerId = DEFAULT_WORKER_ID;
        } else {
            this.workerId = DEFAULT_WORKER_ID;
            this.dataCenterId = DEFAULT_DATA_CENTER_ID;
        }
    }

    /**
     * 为当前工作节点对象生成缓存key
     *
     * @return 字符串，格式为：(dataCenterId, workerId)
     **/
    public String generateCacheKey() {
        return "(" + dataCenterId + "," + workerId + ")";
    }

    /**
     * 格式为：(dataCenterId, workerId)
     **/
    @Override
    public String toString() {
        return "Worker(" +
                "dataCenterId=" + this.dataCenterId +
                ", workerId=" + this.workerId +
                ')';
    }
}
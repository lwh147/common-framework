package com.lwh147.common.mybatisplus.snowflake.service;

import com.lwh147.common.core.exception.CommonExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 当前服务的工作机器ID和其所属数据中心ID封装类
 * <p>
 * 单机模式下默认全部设置为0
 * <p>
 * 集群模式下各工作机器排队依次获取自己的工作机器ID和所属数据中心ID
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
     * 工作机器ID，0-31
     **/
    private Long workerId;
    /**
     * 工作机器属于的数据中心ID，0-31
     **/
    private Long dataCenterId;

    /**
     * 下一个节点
     **/
    public synchronized void next() {
        if (this.workerId < MAX_WORKER_ID) {
            this.workerId++;
        } else if (this.dataCenterId < MAX_DATA_CENTER_ID) {
            this.dataCenterId++;
            this.workerId = DEFAULT_WORKER_ID;
        } else {
            throw CommonExceptionEnum.COMMON_ERROR.toException("工作机器数量已达上限");
        }
    }

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
}

package com.lwh147.common.mybatisplus.config.snowflake;

import com.lwh147.common.core.exception.CommonExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 当前实例的工作机器ID和其所属数据中心ID封装类
 * <p>
 * 单实例模式下默认都是0
 * <p>
 * 多实例模式下各实例之间互相竞争获取自己的工作机器ID和所属数据中心ID
 *
 * @author lwh
 * @date 2021/11/26 9:11
 **/
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
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
     * ID
     **/
    public synchronized void next() {
        if (this.workerId < MAX_WORKER_ID) {
            this.workerId++;
        } else if (this.dataCenterId < MAX_DATA_CENTER_ID) {
            this.dataCenterId++;
            this.workerId = DEFAULT_WORKER_ID;
        } else {
            throw CommonExceptionEnum.COMMON_ERROR.toException("服务实例已达上限");
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

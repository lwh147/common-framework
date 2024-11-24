package com.lwh147.common.core.exception;

import lombok.Getter;

/**
 * 通用开发框架层异常枚举
 *
 * @author lwh
 * @date 2021/10/22 10:49
 **/
@Getter
public enum CommonExceptionEnum implements EnhancedRuntimeExceptionEnum {
    /**
     * 通用开发框架异常
     **/
    COMMON_ERROR("00000", "通用开发框架层执行异常"),

    /**
     * 系统一级宏观错误码
     **/
    SYSTEM_ERROR("S0001", "系统执行出错"),
    /**
     * 系统二级宏观错误码
     **/
    SYSTEM_UNHANDLED_EXCEPTION_ERROR("S0100", "系统未处理的异常"),
    SYSTEM_TIMEOUT_ERROR("S0200", "系统执行超时"),
    SYSTEM_RESOURCE_ERROR("S0300", "系统资源异常"),

    /**
     * 用户端一级宏观错误码
     **/
    CLIENT_ERROR("C0001", "用户端错误"),
    /**
     * 用户端二级宏观错误码
     **/
    CLIENT_REGISTER_ERROR("C0100", "用户注册失败"),
    CLIENT_LOGIN_ERROR("C0200", "用户登录失败"),
    CLIENT_NOT_ALLOW_ERROR("C0300", "访问权限异常"),
    CLIENT_ARGUMENT_NOT_VALID_ERROR("C0400", "用户请求参数非法"),
    CLIENT_REQUEST_ERROR("C0500", "用户请求服务异常"),
    CLIENT_RESOURCE_ERROR("C0600", "用户资源异常"),
    CLIENT_FILE_UPLOAD_ERROR("C0700", "用户上传文件异常"),
    CLIENT_VERSION_ERROR("C0800", "用户当前版本异常"),
    CLIENT_PRIVACY_UNAUTHORIZED_ERROR("C0900", "用户隐私未授权"),
    CLIENT_DEVICE_ERROR("C1000", "用户设备异常"),

    /**
     * 远程调用一级宏观错误码
     **/
    REMOTE_ERROR("R0001", "远程调用出错"),
    /**
     * 远程调用二级宏观错误码
     **/
    REMOTE_TIMEOUT_ERROR("R0100", "远程调用超时"),
    REMOTE_CACHE_ERROR("R0200", "缓存服务出错"),
    REMOTE_DB_ERROR("R0300", "数据库服务出错"),
    REMOTE_MESSAGE_ERROR("R0400", "消息服务出错"),

    ;

    /**
     * 异常枚举编码
     **/
    private final String code;
    /**
     * 异常枚举名称
     **/
    private final String name;

    CommonExceptionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public CommonException toException() {
        return new CommonException(this);
    }

    @Override
    public CommonException toException(String message) {
        return new CommonException(this, message);
    }

    @Override
    public CommonException toException(Throwable cause) {
        return new CommonException(this, cause);
    }

    @Override
    public CommonException toException(String message, Throwable cause) {
        return new CommonException(this, message, cause);
    }
}
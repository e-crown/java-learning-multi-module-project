package com.kv.wc.common.base;

import java.io.Serializable;

import org.slf4j.MDC;

import com.kv.wc.common.enums.ResultStatusEnum;

import lombok.Data;

/**
 * 统一使用返回结果实体类
 *
 *
 */
@Data
public class ResultBO<T> implements Serializable {

    public static final String REQUEST_ID = "requestId";
    private static final long serialVersionUID = -5196002076749493116L;

    private Integer errorCode;
    private Boolean success;
    private String requestId;
    private String message;
    private T data;

    public ResultBO() {
        this(ResultStatusEnum.SUCCESS);
    }

    public ResultBO(final Integer errorCode, final String message, final T data, final boolean success) {
        this.errorCode = errorCode;
        this.message = message;
        this.success = success;
        this.requestId = MDC.get(REQUEST_ID);
        this.data = data;
    }

    public ResultBO(final ResultStatusEnum resultStatusEnum) {
        this.errorCode = resultStatusEnum.getCode();
        this.message = resultStatusEnum.getMessage();
        this.success = isSuccess();
        this.requestId = MDC.get(REQUEST_ID);
    }

    public ResultBO(final ResultStatusEnum baseResultStatusEnum, final T data, final boolean success) {
        this(baseResultStatusEnum);
        this.data = data;
        this.success = success;
    }

    public boolean isSuccess() {
        return ResultStatusEnum.SUCCESS.getCode().equals(this.errorCode);
    }

    public static <T> ResultBO<T> ok(final T data) {
        return new ResultBO(ResultStatusEnum.SUCCESS, data, true);
    }

    public static <T> ResultBO<T> ok(final String message) {
        return new ResultBO(ResultStatusEnum.SUCCESS.getCode(), message, null, true);
    }

    public static <T> ResultBO<T> success(final String message) {
        return new ResultBO(ResultStatusEnum.SUCCESS.getCode(), null, message, true);
    }

    public static <T> ResultBO<T> ok(final String message, final T data) {
        return new ResultBO(ResultStatusEnum.SUCCESS.getCode(), message, data, true);
    }

    public static <T> ResultBO<T> ok() {
        return new ResultBO(ResultStatusEnum.SUCCESS, null, true);
    }

    public static <T> ResultBO<T> error(final ResultStatusEnum baseResultStatusEnum) {
        return new ResultBO(baseResultStatusEnum);
    }

    public static <T> ResultBO<T> error(final String message) {
        return new ResultBO(ResultStatusEnum.FAIL.getCode(), message, null, false);
    }

    public static <T> ResultBO<T> error(final String message, final T data) {
        return new ResultBO(ResultStatusEnum.FAIL.getCode(), message, data, false);
    }

    public static <T> ResultBO<T> error(final String message, final Integer code) {
        return new ResultBO(code, message, null, false);
    }

    public static <T> ResultBO<T> error(final Integer code, final T data) {
        return new ResultBO(code, null, data, false);
    }

    public static <T> ResultBO<T> error(final ResultStatusEnum baseResultStatusEnum, final String errMsg) {
        return new ResultBO(baseResultStatusEnum.getCode(), errMsg, null, false);
    }

}

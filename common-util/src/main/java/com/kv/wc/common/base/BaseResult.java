package com.kv.wc.common.base;

import java.io.Serializable;

import com.kv.wc.common.exception.IErrorCode;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseResult<T> implements Serializable {

    /**
     * 信息反馈
     */
    public static final String SUCCESS_MSG = "SUCCESS";
    public static final String FAILED_MSG = "FAIL";
    /**
     * 状态标示
     */
    public static final int SUCCESS = 200;

    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 数据data
     */
    private T data;

    /**
     * @return success
     */
    public Boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

    public BaseResult() {
        this.code = SUCCESS;
        this.success = true;
        this.message = SUCCESS_MSG;
    }

    public BaseResult(final T data) {
        this.code = SUCCESS;
        this.message = SUCCESS_MSG;
        this.success = true;
        this.data = data;
    }

    public BaseResult(final int code, final String msg) {
        this.code = code;
        this.message = msg;
        if (code == SUCCESS) {
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public BaseResult(final IErrorCode resultStatus) {
        this.code = resultStatus.getErrorCode();
        this.message = resultStatus.getErrorMsg();
        if (SUCCESS == resultStatus.getErrorCode()) {
            this.success = true;
        }
    }

    public BaseResult(final int code, final String msg, final T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
        if (code == SUCCESS) {
            this.success = true;
        } else {
            this.success = false;
        }
    }

    /**
     * 返回结果转换
     * 
     * @param result
     * @param <T>
     * @return
     */
    public static <T> BaseResult result(final BaseResult result) {
        return new BaseResult(result.getCode(), result.getMessage(), result.getData());
    }

    /**
     * 失败结果响应
     *
     * @param status
     * @param <T>
     * @return
     */
    public static <T> BaseResult fail(final IErrorCode status) {
        return new BaseResult<>(status);
    }

    /**
     * 失败结果响应 自定义
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResult fail(final IErrorCode status, final T data) {
        return new BaseResult<>(status.getErrorCode(), status.getErrorMsg(), data);
    }

    /**
     * 成功结果响应
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResult success() {
        return new BaseResult<>();
    }

    /**
     * 成功结果响应
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResult success(final T data) {
        return new BaseResult<>(data);
    }

    /**
     * 成功结果响应 自定义
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResult success(final IErrorCode status, final T data) {
        return new BaseResult<>(status.getErrorCode(), status.getErrorMsg(), data);
    }

}

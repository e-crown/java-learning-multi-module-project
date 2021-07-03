package com.kv.wc.common.exception;

import com.kv.wc.common.enums.ResultStatusEnum;

public class UnAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = -7332456882539877862L;

    private ResultStatusEnum resultStatus;

    public UnAuthorizedException(ResultStatusEnum resultStatus) {
        this(resultStatus.getMessage(), resultStatus);
    }

    public UnAuthorizedException(String message) {
        this(message, ResultStatusEnum.SYSTEM_ERROR);
    }

    public UnAuthorizedException(String message, ResultStatusEnum resultStatus) {
        super(message);
        this.resultStatus = resultStatus;
    }

    public UnAuthorizedException(Exception e) {
        this(ResultStatusEnum.SYSTEM_ERROR, e);
    }

    public UnAuthorizedException(String message, Exception e) {
        this(message, ResultStatusEnum.SYSTEM_ERROR, e);
    }

    public UnAuthorizedException(ResultStatusEnum resultStatus, Exception e) {
        this(resultStatus.getMessage(), resultStatus, e);
    }

    public UnAuthorizedException(String message, ResultStatusEnum resultStatus, Exception e) {
        super(message, e);
        this.resultStatus = resultStatus;
    }

    public ResultStatusEnum getResultStatus() {
        return this.resultStatus;
    }

}

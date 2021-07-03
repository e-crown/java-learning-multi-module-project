package com.kv.wc.common.exception;

import com.kv.wc.common.enums.ResultStatusEnum;
import lombok.ToString;

@ToString
public class ProcessException extends RuntimeException {
    private static final long serialVersionUID = -7332456882539877862L;

    private ResultStatusEnum resultStatus;

    public ProcessException(ResultStatusEnum resultStatus) {
        this(resultStatus.getMessage(), resultStatus);
    }

    public ProcessException(String message) {
        this(message, ResultStatusEnum.SYSTEM_ERROR);
    }

    public ProcessException(String message, ResultStatusEnum resultStatus) {
        super(message);
        this.resultStatus = resultStatus;
    }

    public ProcessException(Exception e) {
        this(ResultStatusEnum.SYSTEM_ERROR, e);
    }

    public ProcessException(String message, Exception e) {
        this(message, ResultStatusEnum.SYSTEM_ERROR, e);
    }

    public ProcessException(ResultStatusEnum resultStatus, Exception e) {
        this(resultStatus.getMessage(), resultStatus, e);
    }

    public ProcessException(String message, ResultStatusEnum resultStatus, Exception e) {
        super(message, e);
        this.resultStatus = resultStatus;
    }

    public ResultStatusEnum getResultStatus() {
        return this.resultStatus;
    }

}

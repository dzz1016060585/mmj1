package com.dzz.transfer.exception;

/**
 * 统一异常
 */
public class TransferException extends Exception {
    private final Integer code;
    private final String message;

    public TransferException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public TransferException(TransferExceptionEnum transferExceptionEnum) {
        this(transferExceptionEnum.getCode(), transferExceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

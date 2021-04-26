package com.dzz.transfer.exception;

import com.dzz.transfer.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理统一异常的handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception: ", e);
        return ApiRestResponse.error(TransferExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(TransferException.class)
    @ResponseBody
    public Object handleTransferException(TransferException e) {
        log.error("TransferException: ", e);
        return ApiRestResponse.error(e.getCode(),e.getMessage());
    }
}

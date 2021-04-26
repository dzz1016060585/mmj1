package com.dzz.transfer.common;

import com.dzz.transfer.exception.TransferExceptionEnum;

/**
 * 通用返回对象,包含了请求处理的状态码和信息
 */
public class ApiRestResponse<T> {
    private Integer status;
    private String msg;
    //返回的对象，但是不固定，所以用泛型
    private T data;
    private static final int OK_CODE=10000;
    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    /**
     * 调用成功，但是不需要返回给前台数据的方法
     * @param <T>
     * @return
     */
    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }

    /**
     * 调用成功，需要返回给前台数据的方法
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ApiRestResponse<T> success(T result) {
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    /**
     * 调用失败的方法
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<>(code, msg);
    }

    /**
     * 调用失败时，使用异常枚举，这样我们不用自己记异常码和信息
     * @param ex
     * @param <T>
     * @return
     */
    public static <T> ApiRestResponse<T> error(TransferExceptionEnum ex) {
        return new ApiRestResponse<>(ex.getCode(), ex.getMsg());
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static int getOkCode() {
        return OK_CODE;
    }

    public static String getOkMsg() {
        return OK_MSG;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

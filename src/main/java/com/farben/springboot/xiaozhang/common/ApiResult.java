package com.farben.springboot.xiaozhang.common;
/**
 * 统一API响应格式
 */
public class ApiResult<T> {
    private int code;         // 状态码
    private String message;   // 提示信息
    private T data;           // 响应数据

    // 成功响应（无数据）
    public static <T> ApiResult<T> success() {
        return success(null);
    }

    // 成功响应（有数据）
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(200, "操作成功", data);
    }

    // 错误响应
    public static <T> ApiResult<T> error(int code, String message) {
        return new ApiResult<>(code, message, null);
    }

    // 带分页的成功响应
    public static <T> ApiResult<PageResult<T>> pageSuccess(PageResult<T> pageResult) {
        return new ApiResult<>(200, "操作成功", pageResult);
    }

    // 构造方法
    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

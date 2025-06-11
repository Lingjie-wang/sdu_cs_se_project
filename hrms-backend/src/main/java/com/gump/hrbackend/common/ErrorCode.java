package com.gump.hrbackend.common;


/**
 * 错误码
 *
 * @author Gump
 */
public enum ErrorCode {
    SUCCESS(0,"ok" ,""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"未登录",""),
    FORBIDDEN_ERROR(40300, "禁止访问",""),
    NOT_FOUND_ERROR(40400, "请求数据不存在",""),
    NO_AUTH(40101,"无权限",""),
    SYSTEM_ERROR(50000,"系统内部异常",""),
    OPERATION_ERROR(50001, "操作失败","");



    private final int code;
    /**
     * 状态码信息
     */
    private final String massage;
    /**
     * 状态码描述（详细）
     */
    private final String description;

    ErrorCode(int code, String massage, String description) {
        this.code = code;
        this.massage = massage;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMassage() {
        return massage;
    }

    public String getDescription() {
        return description;
    }
}

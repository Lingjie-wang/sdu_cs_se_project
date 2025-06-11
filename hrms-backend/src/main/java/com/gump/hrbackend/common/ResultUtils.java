package com.gump.hrbackend.common;


import javax.xml.soap.SAAJResult;

/**
 * 返回工具类
 *
 * @author Gump
 */
public class ResultUtils {

    /**
     * 成功
     * @param data
     * @return 成功
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }


    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String massage,String description){
        return new BaseResponse(errorCode.getCode(),null,massage,description);
    }

    /**
     * 失败
     *
     * @param code
     * @param massage
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String massage,String description){
        return new BaseResponse(code,null,massage,description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String description){
        return new BaseResponse(errorCode.getCode(),errorCode.getMassage(),description);
    }
}

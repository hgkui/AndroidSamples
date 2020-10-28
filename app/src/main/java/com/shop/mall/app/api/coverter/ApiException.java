package com.shop.mall.app.api.coverter;

/**
 * 自定义错误
 *
 * @author fang.xc@outlook.com
 * @date 2018/5/10
 */
public class ApiException extends RuntimeException {
    private String code;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private String errorMsg;


    public ApiException(String code, String msg) {
        this.code = code;
        this.errorMsg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}


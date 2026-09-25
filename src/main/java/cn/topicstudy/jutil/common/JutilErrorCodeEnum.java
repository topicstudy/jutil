package cn.topicstudy.jutil.common;

import cn.topicstudy.jutil.basic.error.BaseErrorCodeEnum;

public enum JutilErrorCodeEnum implements BaseErrorCodeEnum {
    DATE_UTIL_PARAM("U-TS-JUTIL-DATEUTIL-PARAM", "{0}"),

    ;
    private String errorCode;
    private String errorMsg;

    JutilErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

}

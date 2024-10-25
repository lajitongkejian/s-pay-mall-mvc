package cn.nju.edu.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：AppException
 * 作者：tkj
 * 日期：2024/10/25
 */

@EqualsAndHashCode(callSuper = true)
@Data

public class AppException extends RuntimeException{
    private static final long serialVersionUID = -3910396919779410759L;
    private String code;
    private String info;

    public AppException(String code) {
        this.code = code;
    }

    public AppException(String code, Throwable cause) {
        this.code = code;
        super.initCause(cause);
    }

    public AppException(String code, String message) {
        this.code = code;
        this.info = message;
    }

    public AppException(String code, String message, Throwable cause) {
        this.code = code;
        this.info = message;
        super.initCause(cause);
    }

    @Override
    public String toString() {
        return "cn.nju.edu.common.exception.AppException{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                '}';
    }


}

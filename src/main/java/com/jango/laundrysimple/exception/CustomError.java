package com.jango.laundrysimple.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomError extends Throwable {
    String errCode;
    String errDesc;

    public CustomError(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    @Override
    public String toString() {
        return "CustomError{" +
                "errCode='" + errCode + '\'' +
                ", errDesc='" + errDesc + '\'' +
                '}';
    }
}

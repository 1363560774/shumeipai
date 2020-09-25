package com.shumei.pi.exception;

import com.shumei.pi.enums.ExceptionEnum;
import lombok.Getter;

/**
 * @author zhaokai
 */
@Getter
public class PiException extends RuntimeException{
    private int status;

    public PiException(ExceptionEnum e) {
        super(e.getMsg());
        this.status = e.getStatus();
    }

    public PiException(ExceptionEnum e, Throwable cause ) {
        super(e.getMsg(), cause);
        this.status = e.getStatus();
    }
}

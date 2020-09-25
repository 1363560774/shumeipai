package com.shumei.pi.advice;

import com.shumei.pi.exception.PiException;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;


/**
 * @author zhaokai
 */
@Getter
@Setter
public class ExceptionResult {
    private int status;
    private String message;
    private String timestamp;

     public ExceptionResult(PiException e) {
        this.status = e.getStatus();
        this.message = e.getMessage();
        this.timestamp = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }
}

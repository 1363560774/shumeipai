package com.shumei.pi.advice;

import com.shumei.pi.exception.PiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @author zhaokai
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(PiException.class)
    public ResponseEntity<ExceptionResult> handleLyException(PiException e){
        //从异常中获取状态码和提示信息
        return ResponseEntity.status(e.getStatus()).body(new ExceptionResult(e));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e){
        //我们暂定返回状态码为400， 然后从异常中获取友好提示信息
        return ResponseEntity.status(500).body(e.getMessage());
    }
}

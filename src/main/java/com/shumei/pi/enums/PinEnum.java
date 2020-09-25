package com.shumei.pi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2019/12/11 10:23 上午
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PinEnum {

    /**
     * 异常状态
     */
    PWM_SUCCESS(200, "SUCCESS"),
    ;
    private int status;

    private String msg;
}

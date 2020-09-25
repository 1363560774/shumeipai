package com.shumei.pi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author zhaokai
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    /**
     * 异常状态
     */
    PWM_PULSE_RANGE_IS_TOO_LARGE(400, "脉冲范围过大,不能超过3000"),
    PWM_PULSE_WIDTH_IS_TOO_LARGE(400, "脉冲宽度过大"),
    PWM_PULSE_COUNT_IS_TOO_LARGE(400, "脉冲数不得超过脉冲范围"),
    PWM_PULSE_VELOCITY_IS_TOO_LARGE(400, "脉冲速率不能过大,不能小于脉冲数的十倍"),
    PWM_PULSE_TUNING_WIDTH_IS_TOO_LARGE(400, "脉冲范围过大,微调和范围相加不能超过3000"),
    ;
    private int status;

    private String msg;
}

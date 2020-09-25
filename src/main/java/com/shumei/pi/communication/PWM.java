package com.shumei.pi.communication;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import com.shumei.pi.enums.ExceptionEnum;
import com.shumei.pi.enums.PinEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2020/9/24 10:08 下午
 */
@Slf4j
@RestController
@RequestMapping("PWM")
public class PWM {

    /**
     * 设置pwm脉冲
     * 测试参数如下
     * @param range 范围 600
     * @param pulseWidth 脉冲宽度(睡眠时长控制) 500
     * @param pulse 脉冲 5-25
     * @return 消耗时长(毫秒)
     * @throws InterruptedException 线程睡眠异常
     */
    @GetMapping("/testPWM")
    public ResponseEntity<Object> testPWM(@RequestParam("range") Integer range,
                                          @RequestParam("pulseWidth") Integer pulseWidth,
                                          @RequestParam("pulse") Integer pulse) throws InterruptedException {
        String parametersCaveat = checkParameters(range, pulseWidth, pulse);
        if (!parametersCaveat.equals("SUCCESS")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(parametersCaveat);
        }
        final GpioController gpio = GpioFactory.getInstance();
        Pin pin = CommandArgumentParser.getPin(
                RaspiPin.class,
                RaspiPin.GPIO_12
                );
        GpioPinPwmOutput pwm = gpio.provisionSoftPwmOutputPin(pin);
        pwm.setPwmRange(range);
        long startTime = System.currentTimeMillis();
        pwm.setPwm(pulse);
        TimeUnit.MILLISECONDS.sleep(pulseWidth);
        long ensTime = System.currentTimeMillis();
        gpio.shutdown();
        gpio.unprovisionPin(pwm);
        log.info("消耗时长:{}毫秒", ensTime-startTime);
        return ResponseEntity.status(HttpStatus.OK).body(ensTime-startTime);
    }

    private String checkParameters(Integer range, Integer pulseWidth, Integer pulse) {
        if (range == null || range > 600) {
            return ExceptionEnum.PWM_PULSE_RANGE_IS_TOO_LARGE.getMsg();
        }
        if (pulseWidth == null || pulseWidth > 1000) {
            return ExceptionEnum.PWM_PULSE_WIDTH_IS_TOO_LARGE.getMsg();
        }
        if (pulse == null || pulse > 25) {
            return ExceptionEnum.PWM_PULSE_COUNT_IS_TOO_LARGE.getMsg();
        }
        return PinEnum.PWM_SUCCESS.getMsg();
    }
}

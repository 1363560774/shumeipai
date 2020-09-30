package com.shumei.pi.service.impl;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import com.shumei.pi.enums.Enumeration;
import com.shumei.pi.enums.ExceptionEnum;
import com.shumei.pi.enums.PinEnum;
import com.shumei.pi.service.PinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.shumei.pi.enums.Pin.getPin;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2020/9/29 10:15 下午
 */
@Slf4j
@Service
public class PinServiceImpl implements PinService {

    @Override
    public ResponseEntity<Object> setPWMParameters(Integer range, Integer pulseWidth, Integer pulse, Integer pin) throws InterruptedException {
        String parametersCaveat = checkParameters(range, pulseWidth, pulse);
        if (!parametersCaveat.equals(Enumeration.SUCCESS)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(parametersCaveat);
        }
        Pin getPin = getPin(pin);
        final GpioController gpio = GpioFactory.getInstance();
        Pin p = CommandArgumentParser.getPin(
                RaspiPin.class,
                getPin
        );
        GpioPinPwmOutput pwm = gpio.provisionSoftPwmOutputPin(p);
        pwm.setPwmRange(range);
        long startTime = System.currentTimeMillis();
        pwm.setPwm(pulse);
        TimeUnit.MILLISECONDS.sleep(pulseWidth);
        long ensTime = System.currentTimeMillis();
        gpio.unprovisionPin(pwm);
        gpio.shutdown();
        log.info("消耗时长:{}毫秒", ensTime-startTime);
        return ResponseEntity.status(HttpStatus.OK).body(ensTime-startTime);
    }

    @Override
    public String checkParameters(Integer range, Integer pulseWidth, Integer pulse) {
        if (range == null || range > 600) {
            return ExceptionEnum.PWM_PULSE_RANGE_IS_TOO_LARGE.getMsg();
        }
        if (pulseWidth == null || pulseWidth > 1000) {
            return ExceptionEnum.PWM_PULSE_WIDTH_IS_TOO_LARGE.getMsg();
        }
        if (pulse == null || pulse > 25 || pulse < 5) {
            return ExceptionEnum.PWM_PULSE_COUNT_IS_TOO_LARGE.getMsg();
        }
        return PinEnum.PWM_SUCCESS.getMsg();
    }
}

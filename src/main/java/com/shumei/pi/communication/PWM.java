package com.shumei.pi.communication;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
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
     * @param range 范围
     * @param pulseWidth 脉冲宽度(睡眠时长控制)
     * @param pulse 脉冲
     * @param direction 方向
     * @param velocity 速度
     * @param fineAdjustmentCompensation 微调补偿
     * @return 消耗时长(毫秒)
     * @throws InterruptedException 线程睡眠异常
     */
    @GetMapping("/testPWM")
    public ResponseEntity<Object> testPWM(@RequestParam("range") Integer range,
                                          @RequestParam("pulseWidth") Integer pulseWidth,
                                          @RequestParam("pulse") Integer pulse,
                                          @RequestParam("direction") Boolean direction,
                                          @RequestParam("velocity") Integer velocity,
                                          @RequestParam("fineAdjustmentCompensation") Integer fineAdjustmentCompensation) throws InterruptedException {
        String parametersCaveat = checkParameters(range, pulseWidth, pulse, velocity, fineAdjustmentCompensation);
        if (parametersCaveat.equals("")) {
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
        long totalRange = range + fineAdjustmentCompensation;
        if (direction == null || direction) {
            while (pulse < totalRange){
                TimeUnit.MILLISECONDS.sleep(pulseWidth);
                pulse += velocity;
                pwm.setPwm(pulse);
                System.out.println(pulse);
            }
        } else {
            while (pulse + range > totalRange){
                TimeUnit.MILLISECONDS.sleep(pulseWidth);
                pulse -= velocity;
                pwm.setPwm(pulse);
                System.out.println(pulse);
            }
        }
        long ensTime = System.currentTimeMillis();
        gpio.shutdown();
        gpio.unprovisionPin(pwm);


//        String parametersCaveat = checkParameters(range, pulseWidth, pulse, velocity, fineAdjustmentCompensation);
//        Assert.check(parametersCaveat.equals(""), parametersCaveat);
//        long startTime = System.currentTimeMillis();
//        long totalRange = range + fineAdjustmentCompensation;
//        if (direction == null || direction) {
//            while (pulse < totalRange){
//                TimeUnit.MILLISECONDS.sleep(pulseWidth);
//                pulse += velocity;
//                System.out.println(pulse);
//            }
//        } else {
//            while (pulse + range > totalRange){
//                TimeUnit.MILLISECONDS.sleep(pulseWidth);
//                pulse -= velocity;
//                System.out.println(pulse);
//            }
//        }
//        long ensTime = System.currentTimeMillis();
        log.info("消耗时长:{}毫秒", ensTime-startTime);
        return ResponseEntity.status(HttpStatus.OK).body(ensTime-startTime);
    }

    private String checkParameters(Integer range, Integer pulseWidth, Integer pulse,
                                   Integer velocity, Integer fineAdjustmentCompensation) {
        if (range == null || range > 3000) {
            return "脉冲范围过大,不能超过3000";
        }
        if (pulseWidth == null || pulseWidth > 1000) {
            return "脉冲宽度过大";
        }
        if (pulse == null || pulse > range) {
            return "脉冲数不得超过脉冲范围";
        }
        if (velocity == null || pulse/velocity < 10) {
            return "脉冲速率不能过大,不能小于脉冲数的十倍";
        }
        if (fineAdjustmentCompensation == null || fineAdjustmentCompensation + range > 3000) {
            return "脉冲范围过大,微调和范围相加不能超过3000";
        }
        return "";
    }
}

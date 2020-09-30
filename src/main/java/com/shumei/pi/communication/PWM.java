package com.shumei.pi.communication;

import com.shumei.pi.enums.ExceptionEnum;
import com.shumei.pi.exception.PiException;
import com.shumei.pi.service.PinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2020/9/24 10:08 下午
 */
@Slf4j
@RestController
@RequestMapping("pwm")
public class PWM {

    @Autowired
    private PinService pinService;

    /**
     * 设置pwm脉冲
     * 测试参数如下
     * @param range 范围 默认600
     * @param pulseWidth 脉冲宽度(睡眠时长控制) 默认500
     * @param pulse 脉冲 5-25 默认15
     * @param pin 引脚编号 必须是6或12 默认6
     * @return 消耗时长(毫秒)
     * @throws InterruptedException 线程睡眠异常
     */
    @GetMapping("/setPWMParameters")
    public ResponseEntity<Object> setPWMParameters(@RequestParam(value = "range", defaultValue = "600") Integer range,
                                     @RequestParam(value = "pulseWidth", defaultValue = "100") Integer pulseWidth,
                                     @RequestParam(value = "pulse", defaultValue = "15") Integer pulse,
                                     @RequestParam(value = "pin", defaultValue = "6") Integer pin) throws InterruptedException {
        if (!(pin.equals(6) || pin.equals(12))) {
            throw new PiException(ExceptionEnum.PWM_PIN_RANGE_IS_ERROR);
        }
        return pinService.setPWMParameters(range, pulseWidth, pulse, pin);
    }
}

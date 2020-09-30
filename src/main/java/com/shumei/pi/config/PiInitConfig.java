package com.shumei.pi.config;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Collection;

/**
 * 树莓派初始化
 *
 * @author zhaokai
 * @date 2020/9/30 10:13 上午
 */

@Component
public class PiInitConfig {

    @PostConstruct
    public void init(){
        final GpioController gpIo = GpioFactory.getInstance();
        Collection<GpioPin> pins = gpIo.getProvisionedPins();
        pins.forEach(gpIo::unprovisionPin);
        gpIo.shutdown();
    }
}

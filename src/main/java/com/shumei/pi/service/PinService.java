package com.shumei.pi.service;

import org.springframework.http.ResponseEntity;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2020/9/29 10:14 下午
 */
public interface PinService {

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
    ResponseEntity<Object> setPWMParameters(Integer range, Integer pulseWidth, Integer pulse, Integer pin) throws InterruptedException;

    /**
     * 校验请求参数
     * @param range 脉冲范围
     * @param pulseWidth 延时等待时长
     * @param pulse 脉冲
     * @return 参数注册返回SUCCESS否则返回异常信息
     */
    String checkParameters(Integer range, Integer pulseWidth, Integer pulse);
}

package com.shumei.pi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2020/1/9 2:12 下午
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ReturnCode {

    /**
     * 消息状态
     */
    SUCCESS(20000, "成功"),
    BAD_REQUEST(40000, "请求出错"),
    NOT_PRESENCE(40004, "请求不存在"),
    ERROR(50000, "服务器出错"),
    ;
    private int code;

    private String msg;
}

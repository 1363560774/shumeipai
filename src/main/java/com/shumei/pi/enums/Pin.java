package com.shumei.pi.enums;

import com.pi4j.io.gpio.RaspiPin;
import com.shumei.pi.exception.PiException;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2019/12/10 5:19 下午
 */
public class Pin {

    public static com.pi4j.io.gpio.Pin getPin(Integer pin) {
        /*
         * 3v+引脚 表示以3v为起始列从1开始 5v+引脚 表示以5v为起始列从1开始
         * 蓝板实体LED常亮引脚 P13 P06 P21 P08  蓝板型号 PI GPIO-T Plus V2.4
         * 红板实体LED常亮引脚 GPIO13 GPIO6 GPIO21 SP1CEO  红板型号 GPIO Extension Board
         * 1,2,3,5
         * 6,8,9,11
         * 12,14,15,16
         * 21,22,23,27  可作为输出引脚
         */
        switch (pin){
            case 1:
                // 3v15 对应蓝板P05(输出) 对应红板板GPIO5(输出)
                return RaspiPin.GPIO_01;
            case 2:
                // 5v16 对应蓝板P12(输出) 对应红板板GPIO12(输出)
                return RaspiPin.GPIO_02;
            case 3:
                // 5v13 对应蓝板P07(输出) 对应红板板SP1CE1(输出)
                return RaspiPin.GPIO_03;
            case 4:
                // 对应蓝板P
                return RaspiPin.GPIO_04;
            case 5:
                // 3v12 对应蓝板P11(输出) 对应红板板SP1SCLK(输出)
                return RaspiPin.GPIO_05;
            case 6:
                // 3v10 对应蓝板P10(输出) 对应红板板SPMS1(输出)
                return RaspiPin.GPIO_06;
            case 7:
                // 对应蓝板P
                return RaspiPin.GPIO_07;
            case 8:
                // 5v19 对应蓝板P20(输出) 对应红板板GPIO20(输出)
                return RaspiPin.GPIO_08;
            case 9:
                // 5v18 对应蓝板P16(输出) 对应红板板GPIO16(输出)
                return RaspiPin.GPIO_09;
            case 10:
                // 对应蓝板P
                return RaspiPin.GPIO_10;
            case 11:
                // 3v8 对应蓝板P22(输出) 对应红板板GPIO22(输出)
                return RaspiPin.GPIO_11;
            case 12:
                // 5v11 对应蓝板P25(输出) 对应红板板GPIO25(输出)
                return RaspiPin.GPIO_12;
            case 13:
                // 对应蓝板P
                return RaspiPin.GPIO_13;
            case 14:
                // 5v9 对应蓝板P24(输出) 对应红板板GPIO24(输出)
                return RaspiPin.GPIO_14;
            case 15:
                // 3v17 对应蓝板P13(输出) 对应红板板GPIO13(输出)
                return RaspiPin.GPIO_15;
            case 16:
                // 3v16 对应蓝板P06(输出) 对应红板板GPIO6(输出)
                return RaspiPin.GPIO_16;
            case 17:
                // 对应蓝板P
                return RaspiPin.GPIO_17;
            case 18:
                // 对应蓝板P
                return RaspiPin.GPIO_18;
            case 19:
                // 对应蓝板P
                return RaspiPin.GPIO_19;
            case 20:
                // 对应蓝板P
                return RaspiPin.GPIO_20;
            case 21:
                // 5v6 对应蓝板P18(输出) 对应红板板GPIO18(输出)
                return RaspiPin.GPIO_21;
            case 22:
                // 5v5 对应蓝板P15(输出) 对应红板板RXD0(输出)
                return RaspiPin.GPIO_22;
            case 23:
                // 5v4 对应蓝板P14(输出) 对应红板板TXD0(输出)
                return RaspiPin.GPIO_23;
            case 24:
                // 对应蓝板P
                return RaspiPin.GPIO_24;
            case 25:
                // 对应蓝板P
                return RaspiPin.GPIO_25;
            case 26:
                // 对应蓝板P
                return RaspiPin.GPIO_26;
            case 27:
                // 3v3 对应蓝板P03(输出) 对应红板板SCL1(输出)
                return RaspiPin.GPIO_27;
            case 28:
                // 对应蓝板P
                return RaspiPin.GPIO_28;
            case 29:
                // 对应蓝板P
                return RaspiPin.GPIO_29;
            case 30:
                // 对应蓝板P
                return RaspiPin.GPIO_30;
            case 31:
                // 对应蓝板P
                return RaspiPin.GPIO_31;
            default:
                // 没有对应的引脚
                throw new PiException(ExceptionEnum.PARAMETER_CAN_NOT_BE_NULL);
        }
    }
}

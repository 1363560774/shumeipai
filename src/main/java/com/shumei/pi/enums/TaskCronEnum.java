package com.shumei.pi.enums;

/**
 * 注释
 * 常用cron表达式
 * @author zhaokai
 * @date 2020/3/29 5:55 下午
 */
public class TaskCronEnum {

    /*
     * second (秒), minute(分), hour(时), day of month(天), month(月), day of week(周几)
     * 0 * * * * MON-FRI   //周一到周六 每一分钟 0 秒时
     *
     *  举几个例子
     *       [ 0  0/5  14,18  *  *  ? ]  每天14点整，18点整，每隔5分钟执行一次
     *       [ 0  15  10  ?  *  1-6 ]  每个月的周一到周六10点15分执行一次
     *       [ 0  0  2  ?  *  6L ]  每个月的最后一个周六2点执行一次
     *       [ 0  0  2  LW  *  ? ]  每个月的最后一个工作日2点执行一次
     *       [ 0  0  2-4  ?  *  1#1 ]  每个月第一个周一2点到4点期间，每个整点执行一次，应该是三次2点、3点、4点
     */

    /**
     * 每30秒运行一次
     */
    public static final String EVERY_THIRTY_SECONDS = "0/30 * * * * ?";

    /**
     * 每天运行一次
     */
    public static final String EVERY_DAY_SECONDS = "0/30 * * * * ?";

    public static String createLoopCronExpression(int rate, int cycle) {
        String cron = "";
        switch (rate) {
            // 每cycle秒执行一次
            case 0:
                cron = "0/" + cycle + " * * * * ?";
                break;
            // 每cycle分钟执行一次
            case 1:
                cron = "0 0/" + cycle + " * * * ?";
                break;
            // 每cycle小时执行一次
            case 2:
                cron = "0 0 0/" + cycle + " * * ?";
                break;
            // 每cycle天的0点执行一次
            case 3:
                cron = "0 0 0 1/" + cycle + " * ?";
                break;
            // 每cycle月的1号0点执行一次
            case 4:
                cron = "0 0 0 1 1/" + cycle + " ? ";
                break;
            // 每天cycle点执行一次
            case 5:
                cron = "0 0 " + cycle+ "  * * ?";
                break;
            // 默认每小时执行一次
            default:
                cron = "0 0 0/1 * * ?";
                break;
        }
        return cron;
    }
}

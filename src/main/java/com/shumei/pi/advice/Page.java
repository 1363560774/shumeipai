package com.shumei.pi.advice;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2020/2/26 4:10 下午
 */

import lombok.Data;

@Data
public class Page {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页
     */
    private Integer pageNo = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;

    /**
     * 是否有上一页
     */
    private Boolean hasPreviousPage;

    /**
     * 是否有上一页
     */
    private Boolean hasNextPage;

    /**
     * 过滤条件
     */
    private String filter;

    /**
     * 是否排序
     */
    private Boolean sortBy = false;

    /**
     * 排序规则
     */
    private String collation = "";

    public boolean isAutoPaging() {
        return true;
    }

    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }
}

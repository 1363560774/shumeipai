package com.shumei.pi.advice;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zhaokai
 */
@Data
public class PageResult<T> {

    /**
     * 分页数据
     */
    private Page page;

    /**
     * 当前页数据
     */
    private List<T> items;

    /**
     * 当前页附属信息
     */
    private Map<String, String> item;

    /**
     * 总条数
     */
    private Long total;

    public PageResult(Long total, List<T> items, Map<String, String> item) {
        this.total = total;
        this.items = items;
        this.item = item;
    }
}

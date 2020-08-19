package com.lghcode.briefbook.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/19 14:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 一页有多少条记录
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 总记录数
     */
    private long totalRecord;

    /**
     * 要查询的结果集
     */
    private List<T> rows;
}

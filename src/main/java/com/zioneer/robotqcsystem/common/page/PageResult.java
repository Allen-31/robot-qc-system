package com.zioneer.robotqcsystem.common.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "分页结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页数据")
    private List<T> list;
    @Schema(description = "总记录数")
    private Long total;
    @Schema(description = "当前页码")
    private Integer pageNum;
    @Schema(description = "每页条数")
    private Integer pageSize;
    @Schema(description = "总页数")
    private Integer pages;

    public static <T> PageResult<T> empty(PageQuery query) {
        return PageResult.<T>builder()
                .list(Collections.emptyList())
                .total(0L)
                .pageNum(query.getPageNum())
                .pageSize(query.getPageSize())
                .pages(0)
                .build();
    }

    public static <T> PageResult<T> of(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        int pages = (pageSize == null || pageSize <= 0) ? 0 : (int) Math.ceil((double) total / pageSize);
        return PageResult.<T>builder()
                .list(list)
                .total(total)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .pages(pages)
                .build();
    }
}

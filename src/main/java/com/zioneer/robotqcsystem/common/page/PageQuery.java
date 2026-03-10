package com.zioneer.robotqcsystem.common.page;

import com.zioneer.robotqcsystem.common.constant.CommonConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 分页查询参数基类
 */
@Data
@Schema(description = "分页查询参数")
public class PageQuery {

    @Schema(description = "页码", example = "1")
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = CommonConstant.DEFAULT_PAGE_NUM;

    @Schema(description = "每页条数", example = "20")
    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = CommonConstant.MAX_PAGE_SIZE, message = "每页条数不能超过500")
    private Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;

    /** 与接口文档对齐：接收 page 参数时写入 pageNum */
    public void setPage(Integer page) {
        if (page != null) this.pageNum = page;
    }

    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}

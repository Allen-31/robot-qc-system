package com.zioneer.robotqcsystem.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 批量更新菜单排序请求
 */
@Data
@Schema(description = "批量更新菜单排序请求")
public class MenuOrderDTO {

    @Valid
    @Schema(description = "id 与 sortOrder 列表")
    private List<OrderItem> orders;

    @Data
    @Schema(description = "单条排序项")
    public static class OrderItem {
        @NotNull(message = "id 不能为空")
        private Long id;
        @NotNull(message = "sortOrder 不能为空")
        private Integer sortOrder;
    }
}

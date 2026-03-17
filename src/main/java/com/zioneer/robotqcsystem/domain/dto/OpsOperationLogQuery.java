package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Operation log query parameters.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Operation log query")
public class OpsOperationLogQuery extends PageQuery {

    @Schema(description = "Keyword for user/type/ip/request info")
    private String keyword;

    @Schema(description = "Result", example = "success")
    private String result;
}

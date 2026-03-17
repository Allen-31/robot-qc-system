package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * API log query parameters.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "API log query")
public class OpsApiLogQuery extends PageQuery {

    @Schema(description = "Keyword for api name/result/request/response")
    private String keyword;

    @Schema(description = "Call result", example = "success")
    private String callResult;
}

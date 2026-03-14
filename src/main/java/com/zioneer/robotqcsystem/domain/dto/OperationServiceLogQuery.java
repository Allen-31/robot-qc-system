package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Operation service log query parameters.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Service log query")
public class OperationServiceLogQuery extends PageQuery {

    @Schema(description = "Log type", example = "runtime")
    private String type;
}

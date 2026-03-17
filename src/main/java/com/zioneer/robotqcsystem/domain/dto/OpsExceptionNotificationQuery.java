package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exception notification query parameters.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Exception notification query")
public class OpsExceptionNotificationQuery extends PageQuery {

    @Schema(description = "Keyword for code/type/source/issue/robot")
    private String keyword;

    @Schema(description = "Level", example = "P1")
    private String level;

    @Schema(description = "Status", example = "pending")
    private String status;
}

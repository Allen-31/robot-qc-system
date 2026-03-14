package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Operation service list query parameters.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Service list query")
public class OperationServiceQuery extends PageQuery {

    @Schema(description = "Keyword for name/type/version/ip")
    private String keyword;

    @Schema(description = "Service type", example = "business")
    private String type;

    @Schema(description = "Version", example = "v2.3.1")
    private String version;

    @Schema(description = "IP address", example = "10.10.2.11")
    private String ip;

    @Schema(description = "Status", example = "running")
    private String status;
}

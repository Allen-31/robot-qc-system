package com.zioneer.robotqcsystem.domain.dto;

import com.zioneer.robotqcsystem.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Login log query parameters.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Login log query")
public class OpsLoginLogQuery extends PageQuery {

    @Schema(description = "Keyword for user/type/ip/time")
    private String keyword;

    @Schema(description = "Type", example = "login")
    private String type;
}

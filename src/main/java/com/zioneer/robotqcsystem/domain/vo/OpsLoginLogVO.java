package com.zioneer.robotqcsystem.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Login log view object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Login log")
public class OpsLoginLogVO {

    @Schema(description = "Log code", example = "LG-20260304-001")
    private String id;

    @Schema(description = "User code")
    private String user;

    @Schema(description = "Type", example = "login")
    private String type;

    @Schema(description = "IP address")
    private String ip;

    @Schema(description = "Time")
    private LocalDateTime time;
}

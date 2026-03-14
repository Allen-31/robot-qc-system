package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Operation service log view object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Service log")
public class OperationServiceLogVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "Log ID")
    private Long id;

    @Schema(description = "Log name")
    private String logName;

    @Schema(description = "Log type", example = "runtime")
    private String type;

    @Schema(description = "Created time")
    private LocalDateTime createdAt;

    @Schema(description = "Updated time")
    private LocalDateTime updatedAt;

    @Schema(description = "Content")
    private String content;
}

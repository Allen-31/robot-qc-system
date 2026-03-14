package com.zioneer.robotqcsystem.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Operation service view object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Service summary")
public class OperationServiceVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "Service ID")
    private Long id;

    @Schema(description = "Service name")
    private String name;

    @Schema(description = "Service type")
    private String type;

    @Schema(description = "Version")
    private String version;

    @Schema(description = "IP address")
    private String ip;

    @Schema(description = "Status")
    private String status;

    @Schema(description = "CPU usage (%)")
    private BigDecimal cpuUsage;

    @Schema(description = "Memory usage (%)")
    private BigDecimal memoryUsage;

    @Schema(description = "Runtime")
    private String runtime;

    @Schema(description = "Logs")
    private List<OperationServiceLogVO> logs;
}

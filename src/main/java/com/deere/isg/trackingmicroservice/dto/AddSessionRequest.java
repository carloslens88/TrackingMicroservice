package com.deere.isg.trackingmicroservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Carlos Lens
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddSessionRequest {
    @NotNull
    private UUID userId;
    @NotNull
    private UUID machineId;
    @NotNull
    private long orgId;
    @NotNull(message = "The parameter startAt can't be null and must be a valid timestamp format")
    private Timestamp startAt;

    @Override
    public String toString() {
        return "AddSessionRequest [userId=" + userId + ", machineId=" + machineId + ", orgId=" + orgId + ", startAt=" + startAt.toString() + "]";
    }
}

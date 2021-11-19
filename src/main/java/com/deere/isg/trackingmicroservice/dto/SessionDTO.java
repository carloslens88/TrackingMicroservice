package com.deere.isg.trackingmicroservice.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionDTO {
    private UUID userId;
    private UUID machineId;
    private long orgId;
    private Timestamp startAt;

    @Override
    public String toString() {
        return "SessionDTO [userId=" + userId + ", machineId=" + machineId + ", orgId=" + orgId + ", startAt=" + startAt.toString() + "]";
    }
}

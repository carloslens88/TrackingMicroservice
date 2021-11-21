package com.deere.isg.trackingmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EndSessionRequest {
    @NotNull
    private UUID sessionId;
    @NotNull
    private Timestamp endAt;
}

package com.deere.isg.trackingmicroservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class EventRequest {
    @NotNull
    private Timestamp eventAt;
    @NotBlank
    private String eventType;
    private String payload;
}

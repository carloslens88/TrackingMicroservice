package com.deere.isg.trackingmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author Carlos Lens
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventRequest {
    @NotNull
    private Timestamp eventAt;
    @NotBlank
    private String eventType;
    private Map<String,String> payload;

    @Override
    public String toString() {
        return "EventRequest [eventAt=" + eventAt + ", eventType=" + eventType + ", payload=" + payload.toString() + "]";
    }
}

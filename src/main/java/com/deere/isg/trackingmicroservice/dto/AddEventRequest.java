package com.deere.isg.trackingmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * @author Carlos Lens
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddEventRequest {
    @NotNull
    private UUID sessionId;
    @NotEmpty
    private List<@Valid EventRequest> events;
}

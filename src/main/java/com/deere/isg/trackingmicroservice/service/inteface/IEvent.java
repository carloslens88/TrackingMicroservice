package com.deere.isg.trackingmicroservice.service.inteface;

import com.deere.isg.trackingmicroservice.dto.AddEventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Carlos Lens
 */
public interface IEvent {

    void addEvent(AddEventRequest addEventRequest);
}

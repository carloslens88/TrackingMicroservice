package com.deere.isg.trackingmicroservice.service.inteface;

import com.deere.isg.trackingmicroservice.dto.AddEventRequest;

public interface IEvent {

    void addEvent(AddEventRequest addEventRequest);
}

package com.deere.isg.trackingmicroservice.service.inteface;

import com.deere.isg.trackingmicroservice.dto.AddSessionRequest;
import com.deere.isg.trackingmicroservice.dto.EndSessionRequest;
import com.deere.isg.trackingmicroservice.model.Session;

import java.util.Optional;
import java.util.UUID;

public interface ISession {
    Optional<Session> findById(UUID id);
    Session save (AddSessionRequest session);
    Session endSession (EndSessionRequest session);
}

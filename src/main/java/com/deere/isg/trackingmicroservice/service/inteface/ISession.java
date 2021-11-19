package com.deere.isg.trackingmicroservice.service.inteface;

import com.deere.isg.trackingmicroservice.dto.SessionDTO;
import com.deere.isg.trackingmicroservice.model.Session;

import java.util.Optional;
import java.util.UUID;

public interface ISession {
    Optional<Session> findById(UUID id);
    Session save (SessionDTO session);
    Session endSession (SessionDTO session);
}

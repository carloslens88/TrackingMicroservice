package com.deere.isg.trackingmicroservice.repository;

import com.deere.isg.trackingmicroservice.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}
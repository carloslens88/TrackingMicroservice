package com.deere.isg.trackingmicroservice.repository;

import com.deere.isg.trackingmicroservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Carlos Lens
 */
public interface EventRepository extends JpaRepository<Event, UUID> {
}

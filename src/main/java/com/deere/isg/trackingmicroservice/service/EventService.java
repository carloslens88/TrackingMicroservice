package com.deere.isg.trackingmicroservice.service;

import com.deere.isg.trackingmicroservice.dto.AddEventRequest;
import com.deere.isg.trackingmicroservice.dto.EventRequest;
import com.deere.isg.trackingmicroservice.model.Event;
import com.deere.isg.trackingmicroservice.repository.EventRepository;
import com.deere.isg.trackingmicroservice.repository.SessionRepository;
import com.deere.isg.trackingmicroservice.service.inteface.IEvent;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EventService implements IEvent {

    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EventService(EventRepository eventRepository, SessionRepository sessionRepository,
            ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void addEvent(AddEventRequest addEventRequest) {
        List<Event> events = new ArrayList<>();

        try {
            for (EventRequest eventDetail : addEventRequest.getEvents()) {
                Event event = modelMapper.map(eventDetail, Event.class);
                event.setSession(sessionRepository.getById(addEventRequest.getSessionId()));
                events.add(event);
            }

            eventRepository.saveAll(events);

        } catch (Exception ex) {
            log.error("An error occurred when trying to addEvent", ex);
            throw ex;
        }
    }
}

package com.deere.isg.trackingmicroservice.service;;

import com.deere.isg.trackingmicroservice.dto.AddEventRequest;
import com.deere.isg.trackingmicroservice.dto.EventRequest;
import com.deere.isg.trackingmicroservice.model.Event;
import com.deere.isg.trackingmicroservice.model.Session;
import com.deere.isg.trackingmicroservice.repository.EventRepository;
import com.deere.isg.trackingmicroservice.repository.SessionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private static final String SESSION_ID = "fda4fea2-601a-4e3b-a43d-b772c741e40e";
    private static final String EVENT_TYPE = "TRACKING";
    private static final String PAYLOAD = "{\n" +
            "                \"field\" : \"fieldInfo\",\n" +
            "                \"otherField\" : \"otherFieldInfo\"\n" +
            "            }";

    @Mock
    ModelMapper modelMapper;

    @Mock
    EventRepository eventRepository;

    @Mock
    SessionRepository sessionRepository;

    private EventService eventService;
    private List<Event> lstEvents;
    private Event event;
    private Session session;
    private Timestamp eventAt;
    private ObjectMapper mapper;

    @Before
    public void setUp(){
        eventAt = new Timestamp(System.currentTimeMillis());

        session = new Session();
        session.setId(UUID.fromString(SESSION_ID));

        lstEvents = new ArrayList<>();
        event = new Event();
        event.setEventAt(eventAt);
        event.setEventType(EVENT_TYPE);
        event.setSession(session);
        event.setPayload(PAYLOAD);
        lstEvents.add(event);

        mapper = new ObjectMapper();
        eventService = new EventService(eventRepository, sessionRepository, modelMapper);
    }

    @Test
    public void shouldAddEvent() throws JsonProcessingException {
        List<EventRequest> lstEventRequest = new ArrayList<>();
        EventRequest eventRequest = new EventRequest();
        eventRequest.setEventAt(eventAt);
        eventRequest.setEventType(EVENT_TYPE);
        eventRequest.setPayload(mapper.readValue(PAYLOAD, Map.class));
        lstEventRequest.add(eventRequest);

        AddEventRequest addEventRequest = new AddEventRequest();
        addEventRequest.setSessionId(UUID.fromString(SESSION_ID));
        addEventRequest.setEvents(new ArrayList<>(lstEventRequest));

        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(event);
        Mockito.when(sessionRepository.getById(Mockito.any())).thenReturn(session);
        Mockito.when(eventRepository.saveAll(Mockito.any())).thenReturn(lstEvents);

        eventService.addEvent(addEventRequest);

        Assert.assertEquals(addEventRequest.getEvents().size(), lstEvents.size());
    }
}

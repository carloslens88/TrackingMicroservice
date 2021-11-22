package com.deere.isg.trackingmicroservice.controller;

import com.deere.isg.trackingmicroservice.controllers.TrackingController;
import com.deere.isg.trackingmicroservice.model.Event;
import com.deere.isg.trackingmicroservice.model.Session;
import com.deere.isg.trackingmicroservice.service.EventService;
import com.deere.isg.trackingmicroservice.service.SessionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackingController.class)
public class TrackingControllerTest {

    private static final String SESSION_ID = "fda4fea2-601a-4e3b-a43d-b772c741e40e";
    private static final String MACHINE_ID = "f3702b01-2b84-465e-94e1-20ae44006a56";
    private static final String EVENT_TYPE = "TRACKING";
    private static final String PAYLOAD = "{ 'field' : 'someFieldValue' }";
    private static final Long ORG_ID = 11111L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private EventService eventService;

    private Session session;
    private Event event;
    private List<Event> lstEvents;
    private Timestamp startAt;
    private Timestamp eventAt;

    @Before
    public void setUp(){

        startAt = new Timestamp(System.currentTimeMillis());
        eventAt = new Timestamp(System.currentTimeMillis());

        session = new Session();
        session.setId(UUID.fromString(SESSION_ID));
        session.setMachineId(UUID.fromString(MACHINE_ID));
        session.setOrgId(ORG_ID);
        session.setStartAt(startAt);

        lstEvents = new ArrayList<>();
        event = new Event();
        event.setEventAt(eventAt);
        event.setEventType(EVENT_TYPE);
        event.setSession(session);
        event.setPayload(PAYLOAD);
        lstEvents.add(event);
    }

    @Test
    public void shouldBeAbleToStartSession() throws Exception {

        String jsonPayload = "{\n" +
                "    \"userId\": \"32a8ede4-c5c5-4888-9706-b90053c4739f\",\n" +
                "    \"machineId\": \"9ccd7511-f670-4587-954e-407bfac841a2\",\n" +
                "    \"startAt\": \"2021-11-22T08:28:56.782Z\",\n" +
                "    \"orgId\": 11111\n" +
                "}";

        Mockito.when(sessionService.save(Mockito.any())).thenReturn(session);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/startSession")
                .content(jsonPayload)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Assert.assertNotNull(mvcResult.getResponse());
    }

    @Test
    public void shouldBeAbleToGetSessionDetails() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/sessionDetails/" + SESSION_ID);

        Mockito.when(sessionService.findById(Mockito.eq(UUID.fromString(SESSION_ID)))).thenReturn(Optional.ofNullable(session));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertNotNull(mvcResult.getResponse());
    }

    @Test
    public void shouldBeAbleToEndSession() throws Exception {

        String jsonPayload = "{\n" +
                "    \"sessionId\": \"fda4fea2-601a-4e3b-a43d-b772c741e40e\",\n" +
                "    \"endAt\": \"2021-11-22T10:13:56.782+00:00\"\n" +
                "}";

        Mockito.when(sessionService.endSession(Mockito.any())).thenReturn(session);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/endSession")
                .content(jsonPayload)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertNotNull(mvcResult.getResponse());
    }

    @Test
    public void shouldBeAbleToAddEvent() throws Exception {

        String jsonPayload = "{\n" +
                "    \"sessionId\": \"fda4fea2-601a-4e3b-a43d-b772c741e40e\",\n" +
                "    \"events\": [\n" +
                "        {\n" +
                "            \"eventAt\": \"2021-11-22T10:09:56.782+00:00\",\n" +
                "            \"eventType\": \"TrackingFieldsForSession\",\n" +
                "            \"payload\": {\n" +
                "                \"field\" : \"fieldInfo\",\n" +
                "                \"otherField\" : \"otherFieldInfo\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"eventAt\": \"2021-11-22T10:13:56.782+00:00\",\n" +
                "            \"eventType\": \"TrackingFieldsForSession\",\n" +
                "            \"payload\": {\n" +
                "                \"field\" : \"fieldInfo2\",\n" +
                "                \"otherField\" : \"otherFieldInfo2\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Mockito.doNothing().when(eventService).addEvent(Mockito.any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/addEvent")
                .content(jsonPayload)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Assert.assertNotNull(mvcResult.getResponse());
    }
}

package com.deere.isg.trackingmicroservice.service;

import com.deere.isg.trackingmicroservice.dto.AddSessionRequest;
import com.deere.isg.trackingmicroservice.dto.EndSessionRequest;
import com.deere.isg.trackingmicroservice.model.Session;
import com.deere.isg.trackingmicroservice.repository.SessionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

    private static final String SESSION_ID = "fda4fea2-601a-4e3b-a43d-b772c741e40e";
    private static final String MACHINE_ID = "f3702b01-2b84-465e-94e1-20ae44006a56";
    private static final Long ORG_ID = 11111L;

    @Mock
    ModelMapper modelMapper;

    @Mock
    SessionRepository sessionRepository;

    private Timestamp startAt;
    private Session session;
    private SessionService sessionService;
    private Timestamp endAt;

    @Before
    public void setUp(){
        startAt = new Timestamp(System.currentTimeMillis());

        session = new Session();
        session.setId(UUID.fromString(SESSION_ID));
        session.setMachineId(UUID.fromString(MACHINE_ID));
        session.setOrgId(ORG_ID);
        session.setStartAt(startAt);

        sessionService = new SessionService(sessionRepository, modelMapper);
    }

    @Test
    public void shouldGetSessionDetailsById() {
        Mockito.when(sessionRepository.findById(Mockito.eq(UUID.fromString(SESSION_ID)))).thenReturn(Optional.ofNullable(session));

        sessionService.findById(UUID.fromString(SESSION_ID));

        Assert.assertEquals(session.getId(), UUID.fromString(SESSION_ID));
    }

    @Test
    public void shouldAddSession(){
        AddSessionRequest addSessionRequest = new AddSessionRequest();
        addSessionRequest.setMachineId(UUID.fromString(MACHINE_ID));
        addSessionRequest.setOrgId(ORG_ID);
        addSessionRequest.setStartAt(startAt);

        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(session);
        Mockito.when(sessionRepository.save(Mockito.eq(session))).thenReturn(session);

        sessionService.save(addSessionRequest);

        Assert.assertEquals(session.getId(), UUID.fromString(SESSION_ID));
        Assert.assertEquals(addSessionRequest.getMachineId(), session.getMachineId());
    }

    @Test
    public void shouldEndSession(){
        endAt = new Timestamp(System.currentTimeMillis());
        session.setEndAt(endAt);

        EndSessionRequest endSessionRequest = new EndSessionRequest();
        endSessionRequest.setSessionId(UUID.fromString(SESSION_ID));
        endSessionRequest.setEndAt(endAt);

        Mockito.when(sessionRepository.findById(Mockito.eq(UUID.fromString(SESSION_ID)))).thenReturn(Optional.ofNullable(session));
        Mockito.when(sessionRepository.save(Mockito.eq(session))).thenReturn(session);

        sessionService.endSession(endSessionRequest);

        Assert.assertEquals(session.getId(), UUID.fromString(SESSION_ID));
        Assert.assertEquals(endSessionRequest.getEndAt(), session.getEndAt());
    }
}

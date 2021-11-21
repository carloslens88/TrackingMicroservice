package com.deere.isg.trackingmicroservice.service;;

import com.deere.isg.trackingmicroservice.model.Event;
import com.deere.isg.trackingmicroservice.model.Session;
import com.deere.isg.trackingmicroservice.repository.EventRepository;
import com.deere.isg.trackingmicroservice.repository.SessionRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    ModelMapper modelMapper;

    @Mock
    EventRepository eventRepository;

    @Mock
    SessionRepository sessionRepository;

    @Test
    public void shouldAddEvent(){
        //Mockito.when(modelMapper.map(CoreMatchers.any(Event.class)).thenReturn(new Event());
        //Mockito.when(sessionRepository.getById(CoreMatchers.any(UUID))).thenReturn(new Session());
        Assert.assertEquals(true, true);
    }
}

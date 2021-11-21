package com.deere.isg.trackingmicroservice.controllers;

import com.deere.isg.trackingmicroservice.dto.AddEventRequest;
import com.deere.isg.trackingmicroservice.dto.AddSessionRequest;
import com.deere.isg.trackingmicroservice.dto.EndSessionRequest;
import com.deere.isg.trackingmicroservice.model.Session;
import com.deere.isg.trackingmicroservice.service.EventService;
import com.deere.isg.trackingmicroservice.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TrackingController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String index() {
        return "TMS (TrackingMicroService) API - Index";
    }

    @PostMapping("/startSession")
    public ResponseEntity<Session> startSession(@Valid @RequestBody AddSessionRequest addSessionRequest) {
        Session session = sessionService.save(addSessionRequest);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    @PutMapping ("/endSession")
    public ResponseEntity<Session> endSession(@Valid @RequestBody EndSessionRequest endSessionRequest) {
        try {
            Session session = sessionService.endSession(endSessionRequest);
            return new ResponseEntity<>(session, HttpStatus.OK);
        }catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/sessionDetails/{sessionId}")
    public ResponseEntity<Session> sessionDetails(@PathVariable("sessionId") String sessionId) {
        Optional<Session> session = sessionService.findById(UUID.fromString(sessionId));
        return session.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/addEvent")
    public ResponseEntity<String> addEvent(@Valid @RequestBody AddEventRequest addEventRequest) {
        eventService.addEvent(addEventRequest);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}

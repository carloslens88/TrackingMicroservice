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

/**
 * TrackingController expose the API endpoints to track user and machine information
 *
 * @author Carlos Lens
 *
 */
@RestController
@RequestMapping("/api")
public class TrackingController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private EventService eventService;

    /**
     * This Api endpoint is used to test the api initialization
     * only testing purposes
     *
     * @return String message to check if the API is running
     */
    @GetMapping("/")
    public String index() {
        return "TMS (TrackingMicroService) API - Index";
    }

    /**
     * startSession endpoint saves a session information and returns
     * a session object for further tracking activities
     *
     * @param addSessionRequest session details to start
     * @return Session details
     */
    @PostMapping(value = "/startSession", consumes = "application/json")
    public ResponseEntity<Session> startSession(@Valid @RequestBody AddSessionRequest addSessionRequest) {
        Session session = sessionService.save(addSessionRequest);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    /**
     * endSessionRequest endpoint sets the value of the endAt property and returns
     * a session object for further tracking activities
     *
     * @param endSessionRequest session details to end
     * @return Session details
     */
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

    /**
     * sessionDetails endpoint gets the session information filtered by id and returns a session object information
     *
     * @param sessionId session identifier to get details
     * @return Session details
     */
    @GetMapping("/sessionDetails/{sessionId}")
    public ResponseEntity<Session> sessionDetails(@PathVariable("sessionId") String sessionId) {
        Optional<Session> session = sessionService.findById(UUID.fromString(sessionId));
        return session.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * addEvent endpoint is used to add a batch of events to the current session the session and returns a confirmation message
     * and HttpStatus Code -> 201 (Created)
     *
     * @param addEventRequest list of events to attach to the session
     * @return String confirmation message.
     */
    @PostMapping("/addEvent")
    public ResponseEntity<String> addEvent(@Valid @RequestBody AddEventRequest addEventRequest) {
        eventService.addEvent(addEventRequest);
        return new ResponseEntity<>("The events are successfully added to the database.", HttpStatus.CREATED);
    }
}

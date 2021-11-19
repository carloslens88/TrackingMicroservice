package com.deere.isg.trackingmicroservice.controllers;

import com.deere.isg.trackingmicroservice.dto.SessionDTO;
import com.deere.isg.trackingmicroservice.model.Session;
import com.deere.isg.trackingmicroservice.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TrackingController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/startSession")
    public ResponseEntity<Session> startSession(@RequestBody SessionDTO sessionDto) {
        Session session = sessionService.save(sessionDto);
        return new ResponseEntity<Session>(session, HttpStatus.CREATED);
    }

    @PutMapping ("/endSession")
    public ResponseEntity<Session> endSession(@RequestBody SessionDTO sessionDto) {
        try {
            Session session = sessionService.endSession(sessionDto);
            return new ResponseEntity<Session>(session, HttpStatus.OK);
        }catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

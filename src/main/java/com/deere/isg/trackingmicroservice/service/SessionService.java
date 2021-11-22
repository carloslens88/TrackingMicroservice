package com.deere.isg.trackingmicroservice.service;

import com.deere.isg.trackingmicroservice.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import com.deere.isg.trackingmicroservice.model.Session;
import com.deere.isg.trackingmicroservice.repository.SessionRepository;
import com.deere.isg.trackingmicroservice.service.inteface.ISession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SessionService implements ISession {

    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SessionService(SessionRepository sessionRepository, ModelMapper modelMapper) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Session> findById(UUID id) {
        return sessionRepository.findById(id);
    }

    /**
     * This method is used to create (start) a session and returns the session information.
     *
     * @param addSessionRequest session details to start
     * @return Session session information.
     */
    @Override
    public Session save(AddSessionRequest addSessionRequest) {
        try {
            Session session = modelMapper.map(addSessionRequest, Session.class);
            return sessionRepository.save(session);
        } catch (Exception ex) {
            log.error("Error trying to save the trace addSession.", ex);
            throw ex;
        }
    }

    /**
     * This method is used to update the entAt session attribute and returns the session information.
     *
     * @param endSessionRequest session details to end
     * @throws NullPointerException when session doesn't exist
     * @return Session details of the session
     */
    @Override
    public Session endSession(EndSessionRequest endSessionRequest) throws NullPointerException {
        try {
            Optional<Session> session = sessionRepository.findById(endSessionRequest.getSessionId());
            if (session.isPresent()) {
                session.get().setEndAt(endSessionRequest.getEndAt());
                return sessionRepository.save(session.get());
            } else {
                throw new NullPointerException("Session guid doesn't exists.");
            }
        }catch (Exception ex) {
            log.error("Error trying to save the trace endSession.", ex);
            throw ex;
        }
    }
}

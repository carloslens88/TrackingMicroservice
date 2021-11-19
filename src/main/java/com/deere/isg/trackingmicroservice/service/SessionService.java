package com.deere.isg.trackingmicroservice.service;

import com.deere.isg.trackingmicroservice.dto.SessionDTO;
import org.modelmapper.ModelMapper;
import com.deere.isg.trackingmicroservice.model.Session;
import com.deere.isg.trackingmicroservice.repository.SessionRepository;
import com.deere.isg.trackingmicroservice.service.inteface.ISession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService implements ISession {
    SessionRepository sessionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Optional<Session> findById(UUID id) {
        return sessionRepository.findById(id);
    }

    @Override
    public Session save(SessionDTO sessionDto) {
        try {
            Session session = modelMapper.map(sessionDto, Session.class);
            session.setId(UUID.randomUUID());
            return sessionRepository.save(session);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Session endSession(SessionDTO sessionDto) throws NullPointerException {
        try {
            Optional<Session> session = sessionRepository.findById(sessionDto.getMachineId());
            if (session.isPresent()) {
                session.get().setEndAt(sessionDto.getStartAt());
                return sessionRepository.save(session.get());
            } else {
                throw new NullPointerException("Session guid doesn't exists.");
            }
        }catch (Exception ex) {
            throw ex;
        }
    }
}

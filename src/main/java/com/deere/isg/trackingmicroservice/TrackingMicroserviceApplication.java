package com.deere.isg.trackingmicroservice;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Carlos Lens
 */
@SpringBootApplication
@EnableAsync
public class  TrackingMicroserviceApplication {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     * Main method of the app - Running app as Spring Boot application.
     *
     * @param args array of args to initialize the app
     */
    public static void main(String[] args) {
        SpringApplication.run(TrackingMicroserviceApplication.class, args);
    }
}

package com.jango.laundrysimple.service;

import com.jango.laundrysimple.model.WashRequest;
import com.jango.laundrysimple.pojo.enums.WashStatus;
import com.jango.laundrysimple.pojo.request.WashRequestPojo;
import com.jango.laundrysimple.pojo.response.Response;
import com.jango.laundrysimple.repository.UserRepository;
import com.jango.laundrysimple.repository.WashRequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class WashRequestService {

    @Autowired
    private WashRequestRepository washRequestRepository;

    @Autowired
    private UserRepository userRepository;


    public Response makeRequest(WashRequestPojo washRequest) {
        try {
            return userRepository.findById(washRequest.getUserId()).map(wRequest -> {
                WashRequest mWashRequest = new ModelMapper().map(washRequest, WashRequest.class);
                mWashRequest.setId(0L);
//                mWashRequest.setClothType(washRequest.getClothType());
                mWashRequest.setRequestTime(LocalDateTime.now());
                mWashRequest.setStatus(WashStatus.RECEIVED);
                washRequestRepository.save(mWashRequest);
                return new Response.ResponseBuilder<>()
                        .code(200)
                        .status(true)
                        .message("Request made successfully")
                        .build();
            }).orElse(
                    new Response.ResponseBuilder<>()
                    .code(404)
                    .status(false)
                    .message("User Id Provided not found")
                    .build());
        } catch (Exception e) {
            return new Response.ResponseBuilder<>()
                    .code(500)
                    .status(false)
                    .message("Error Occurred")
                    .build();
        }
    }

    public Response findAllRequestByUser(Long userId) {
        return new Response.ResponseBuilder<>()
                .data(washRequestRepository.findByUserId(userId))
                .code(200)
                .status(true)
                .message("Request made successfully")
                .build();
    }

    public Response findAllWashRequest() {
        return new Response.ResponseBuilder<>()
                .data(washRequestRepository.findAll())
                .code(200)
                .status(true)
                .message("Request made successfully")
                .build();
    }

    public Response getWashRequest(Long washRequestId) {
        return washRequestRepository.findById(washRequestId).map(washRequest -> {
            return new Response.ResponseBuilder<>()
                    .data(washRequest)
                    .code(200)
                    .status(true)
                    .message("Request made successfully")
                    .build();
        }).orElse(
                new Response.ResponseBuilder<>()
                        .code(404)
                        .status(false)
                        .message("Id Provided not found")
                        .build());
    }

    public Response changeWashRequestState(Long washRequestId, WashStatus status) {
        return washRequestRepository.findById(washRequestId).map(washRequest -> {
            washRequest.setStatus(status);
            washRequestRepository.save(washRequest);
            return new Response.ResponseBuilder<>()
                    .code(200)
                    .status(true)
                    .message("Request made successfully")
                    .build();
        }).orElse(
                new Response.ResponseBuilder<>()
                        .code(404)
                        .status(false)
                        .message(" Id Provided not found")
                        .build());
    }

}

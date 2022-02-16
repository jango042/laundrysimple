package com.jango.laundrysimple.controller;

import com.jango.laundrysimple.pojo.enums.WashStatus;
import com.jango.laundrysimple.pojo.request.WashRequestPojo;
import com.jango.laundrysimple.pojo.response.Response;
import com.jango.laundrysimple.service.WashRequestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wash/request")
public class WashRequestController {

    @Autowired
    private WashRequestService washRequestService;




    @ApiOperation(value = "Make Wash Request", notes = "This endpoint makes user wash request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @PostMapping("/create")
    public ResponseEntity<Response> makeWashRequest(@RequestBody WashRequestPojo washRequest) {
        Response response = washRequestService.makeRequest(washRequest);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "History of users wash request", notes = "this endpoint list all the wash request made by a user, using the user id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/find/all/request/by/user/{userId}")
    public ResponseEntity<Response> findAllRequestByUser(@PathVariable("userId") Long userId) {
        Response response = washRequestService.findAllRequestByUser(userId);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "find all user wash request", notes = "This endpoint Fetchs all user wash request in the system")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/find/all/request")
    public ResponseEntity<Response> findAllRequest() {
        Response response = washRequestService.findAllWashRequest();
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "get user wash request", notes = "This endpoint get a single user wash request in the system using washRequestId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/get/wash/request/{washRequestId}")
    public ResponseEntity<Response> getWashRequest(@PathVariable("washRequestId") Long washRequestId) {
        Response response = washRequestService.getWashRequest(washRequestId);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Change user wash request Status", notes = "This endpoint changes a single user wash request status in the system using washRequestId and WashRequestStatus and enum")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @PostMapping("/change/wash/request/status")
    public ResponseEntity<Response> changeWashRequestStatus(@RequestParam("washRequestId") Long washRequestId, @RequestParam("status")WashStatus status) {
        Response response = washRequestService.changeWashRequestState(washRequestId, status);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}

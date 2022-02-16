package com.jango.laundrysimple.controller;

import com.jango.laundrysimple.exception.CustomError;
import com.jango.laundrysimple.pojo.request.LoginDetailsPojo;
import com.jango.laundrysimple.pojo.request.UserEditRequest;
import com.jango.laundrysimple.pojo.request.UserRequest;
import com.jango.laundrysimple.pojo.response.Response;
import com.jango.laundrysimple.service.UserServiceImpl;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @ApiOperation("User login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response Headers")})
    @PostMapping("/login")
    public void Login(@RequestBody LoginDetailsPojo loginRequestModel) throws CustomError {

        throw new IllegalStateException("This Method should not be called!");
    }

    @ApiOperation(value = "Create User", notes = "Create User")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @PostMapping("/create")
    public ResponseEntity<Response> createUser(@RequestBody UserRequest userRequest) {
        log.info("Request Body::: {}", userRequest);
        Response response = userServiceImpl.createUser(userRequest);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Edit few user details", notes = "Edit Few user details")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @PutMapping("/edit")
    public ResponseEntity<Response> editUser(@RequestBody UserEditRequest userRequest) {
        Response response = userServiceImpl.editUser(userRequest);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Find All Users", notes = "find All Users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/find/all")
    public ResponseEntity<Response> findAll() {
        Response response = userServiceImpl.findAllUsers();
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "find User by email", notes = "Find User by email")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/find/by/email/{email}")
    public ResponseEntity<Response> findByEmail(@PathVariable("email") String email) {
        Response response = userServiceImpl.findUserByEmail(email);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Delete User", notes = "Delete User")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteUser(@RequestParam("id") Long id) {
        Response response = userServiceImpl.deleteUser(id);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }



}

package com.jango.laundrysimple.pojo.request;

import com.jango.laundrysimple.pojo.AddressPojo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserRequest {
    private Long id;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDateTime registrationTime;
    private String phone;
    private String gender;
    private String password;
    private AddressPojo address;
    private String firstName;
    private String lastName;
}

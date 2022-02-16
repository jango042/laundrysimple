package com.jango.laundrysimple.pojo.response;

import com.jango.laundrysimple.pojo.AddressPojo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponsePojo {
    private Long id;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDateTime registrationTime;
    private String phone;
    private String gender;
    private String firstName;
    private String lastName;
    private AddressPojo address;

}

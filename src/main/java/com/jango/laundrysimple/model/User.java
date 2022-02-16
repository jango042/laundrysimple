package com.jango.laundrysimple.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Email field cannot be empty")
    private String email;
    @NotNull(message = "Password field cannot be empty")
    private String password;
    private LocalDate dateOfBirth;
    private LocalDateTime registrationTime;
    @NotNull(message = "Phone Number field cannot be empty")
    private String phone;
    private String gender;
    private Long addressId;
    @NotNull(message = "First Name field cannot be empty")
    private String firstName;
    @NotNull(message = "Last Name field cannot be empty")
    private String lastName;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Roles> rolesList;
}

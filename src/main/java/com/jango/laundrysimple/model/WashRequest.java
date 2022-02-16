package com.jango.laundrysimple.model;

import com.jango.laundrysimple.pojo.enums.WashStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class WashRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clothType;
    private int quantity;
    @NotNull(message = "This field cannot be null")
    private Long userId;
    private LocalDateTime requestTime;
    @Enumerated
    private WashStatus status;

}

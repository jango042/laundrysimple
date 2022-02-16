package com.jango.laundrysimple.pojo.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WashRequestPojo {
    private String clothType;
    private int quantity;
    private Long userId;
}

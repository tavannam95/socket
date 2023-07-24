package com.example.socketdemo.model;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

import java.security.Principal;

/**
 * @author Nguyen Trung Anh
 * @created 3/6/2023
 */

//@ApiModel(description = "User Websocket")
public class UserWs implements Principal {
//    @ApiModelProperty(notes = "user name", value= "anhnt")
    private String name;

    public UserWs(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

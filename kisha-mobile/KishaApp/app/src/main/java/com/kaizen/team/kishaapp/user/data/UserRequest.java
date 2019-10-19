package com.kaizen.team.kishaapp.user.data;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */

@Getter
@Setter
public class UserRequest {

    private String number;
    private String password;

    public UserRequest(String number, String password) {
        this.number = number;
        this.password = password;
    }
}

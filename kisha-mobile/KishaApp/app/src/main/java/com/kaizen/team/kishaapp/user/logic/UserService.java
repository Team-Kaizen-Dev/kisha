package com.kaizen.team.kishaapp.user.logic;


import com.kaizen.team.kishaapp.user.data.User;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public interface UserService {

    String LOGIN_USER = "/api/user/login";
    String CREATE_USER = "/api/user/register";

    User loginUser(String number, String password) throws Exception;

    User createUser(User user) throws Exception;
}

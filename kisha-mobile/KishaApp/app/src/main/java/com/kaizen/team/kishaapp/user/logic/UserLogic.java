package com.kaizen.team.kishaapp.user.logic;


import com.kaizen.team.kishaapp.user.data.User;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public interface UserLogic {

    User loginUser(String userName, String password) throws Exception;
    User createUser(User user) throws Exception;
}

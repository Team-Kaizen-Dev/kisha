package com.kaizen.team.kishaapp.user.logic;


import com.kaizen.team.kishaapp.user.data.User;
import com.satellite.retrofit.retrofit.ServiceHandler;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class UserLogicImpl implements UserLogic {

    private static UserLogic INSTANCE;


    private UserService service;

    private UserLogicImpl(ServiceHandler hander) {
        this.service = new UserServiceImpl(hander);
    }

    public static UserLogic getInstance(ServiceHandler handler){
        if(INSTANCE == null){
            INSTANCE = new UserLogicImpl(handler);
        }
        return INSTANCE;
    }

    @Override
    public User loginUser(String number, String password) throws Exception {
        return service.loginUser(number, password);
    }

    @Override
    public User createUser(User user) throws Exception {
        return service.createUser(user);
    }

}

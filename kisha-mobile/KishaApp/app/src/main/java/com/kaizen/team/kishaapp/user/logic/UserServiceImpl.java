package com.kaizen.team.kishaapp.user.logic;


import com.kaizen.team.kishaapp.user.data.User;
import com.satellite.retrofit.retrofit.IServiceHandler;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class UserServiceImpl implements UserService{
    private IServiceHandler handler;

    public UserServiceImpl(IServiceHandler handler) {
        this.handler = handler;
    }

    @Override
    public User loginUser(String number, String password) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(LOGIN_USER).append("?username=").append(number).append("&password=").append(password);
        return (User) handler.callForSycnPostStringBody(builder.toString(), "", "", User.class);
    }

    @Override
    public User createUser(User user) throws Exception {
     return (User) handler.callForSyncPostObjectBody(CREATE_USER, user, "", User.class);
    }
}

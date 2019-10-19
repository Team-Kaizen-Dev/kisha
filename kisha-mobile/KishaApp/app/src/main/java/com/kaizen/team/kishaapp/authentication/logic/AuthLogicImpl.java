package com.kaizen.team.kishaapp.authentication.logic;

import com.satellite.retrofit.retrofit.ServiceHandler;

public class AuthLogicImpl extends AuthLogic{
    private static AuthLogicImpl INSTANCE;

    private AuthLogicImpl(ServiceHandler oauthHandler) {
        super(oauthHandler);
    }

    public static AuthLogicImpl getInstance(ServiceHandler oauthHandler) {
        if(INSTANCE == null) {
            INSTANCE = new AuthLogicImpl(oauthHandler);
        }
        return INSTANCE;
    }
}

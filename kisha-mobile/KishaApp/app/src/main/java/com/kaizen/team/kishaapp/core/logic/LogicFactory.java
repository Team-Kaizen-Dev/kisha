package com.kaizen.team.kishaapp.core.logic;


import com.kaizen.team.kishaapp.authentication.logic.AuthLogic;
import com.kaizen.team.kishaapp.authentication.logic.AuthLogicImpl;
import com.kaizen.team.kishaapp.core.ws.RestHelper;
import com.satellite.retrofit.retrofit.ServiceHandler;
import com.satellite.retrofit.retrofit.ServiceMethod;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class LogicFactory {

    public static AuthLogic getAuthLogic() {
        return AuthLogicImpl.getInstance(
                new ServiceHandler(RestHelper.getInstance().createAuthApiInstance(ServiceMethod.class)));
    }
}

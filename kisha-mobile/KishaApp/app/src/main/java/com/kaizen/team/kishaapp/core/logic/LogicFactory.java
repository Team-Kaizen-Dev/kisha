package com.kaizen.team.kishaapp.core.logic;


import com.kaizen.team.kishaapp.core.ws.RestHelper;
import com.kaizen.team.kishaapp.datalog.logic.DataLogLogic;
import com.kaizen.team.kishaapp.datalog.logic.DataLogLogicImpl;
import com.kaizen.team.kishaapp.user.logic.UserLogic;
import com.kaizen.team.kishaapp.user.logic.UserLogicImpl;
import com.satellite.retrofit.retrofit.ServiceHandler;
import com.satellite.retrofit.retrofit.ServiceMethod;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class LogicFactory {

    public static UserLogic getAuthLogic() {
        return UserLogicImpl.getInstance(
                new ServiceHandler(RestHelper.getInstance().createApiInstance(ServiceMethod.class)));
    }

    public static DataLogLogic getDataLogLogic() {
        return DataLogLogicImpl.getInstance(new ServiceHandler(RestHelper.getInstance().createApiInstance(ServiceMethod.class)));
    }
}

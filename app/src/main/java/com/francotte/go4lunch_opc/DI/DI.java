package com.francotte.go4lunch_opc.DI;


import com.francotte.go4lunch_opc.service.authentication.AuthenticationService;
import com.francotte.go4lunch_opc.service.authentication.AuthenticationServiceHelper;
import com.francotte.go4lunch_opc.service.markers.MapService;
import com.francotte.go4lunch_opc.service.markers.MapServiceHelper;

public class DI {

    private static final MapService serviceMap = new MapServiceHelper();
    private static final AuthenticationService serviceAuthentication = new AuthenticationServiceHelper();

    public static MapService getMapApiService() {
        return serviceMap;
    }

    public static AuthenticationService getServiceAuthentication() {
        return serviceAuthentication;
    }

}


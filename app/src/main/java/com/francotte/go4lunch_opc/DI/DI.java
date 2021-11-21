package com.francotte.go4lunch_opc.DI;


import com.francotte.go4lunch_opc.service.AuthenticationService;
import com.francotte.go4lunch_opc.service.AuthenticationServiceHelper;
import com.francotte.go4lunch_opc.service.MapService;
import com.francotte.go4lunch_opc.service.MapServiceHelper;

/**
 * Create By Damien De Lombaert
 * 2020
 */
public class DI {

    private static final MapService serviceMap = new MapServiceHelper();
    private static final AuthenticationService serviceAuthentication = new AuthenticationServiceHelper();

    public static MapService getMapApiService() {
        return serviceMap;
    }
    public static AuthenticationService getServiceAuthentication(){return serviceAuthentication; }

     }


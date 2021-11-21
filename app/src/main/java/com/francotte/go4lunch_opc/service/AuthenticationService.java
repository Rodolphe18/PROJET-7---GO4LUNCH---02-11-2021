package com.francotte.go4lunch_opc.service;

import android.content.Intent;

public interface AuthenticationService {
    Intent getAuthUIOfSignWithFacebook();
    Intent getAuthUIOfSignWithGoogle();
}

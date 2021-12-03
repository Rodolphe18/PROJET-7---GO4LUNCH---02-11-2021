package com.francotte.go4lunch_opc.service.authentication;

import android.content.Intent;

public interface AuthenticationService {
    Intent getAuthUIOfSignWithFacebook();
    Intent getAuthUIOfSignWithGoogle();
    Intent getAuthUIOfSignWithTwitter();
}

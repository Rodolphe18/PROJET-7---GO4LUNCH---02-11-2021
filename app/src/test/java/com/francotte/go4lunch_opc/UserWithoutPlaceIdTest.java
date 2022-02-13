package com.francotte.go4lunch_opc;

import androidx.annotation.Nullable;

import com.francotte.go4lunch_opc.models.UserWithoutPlaceId;

import junit.framework.TestCase;

public class UserWithoutPlaceIdTest extends TestCase {

    private String userId = "id"  ;
    private String username = "name";
    @Nullable
    private String urlPicture = "urlPicture";
    //TOKEN
    @Nullable
    private String token = "token";


    UserWithoutPlaceId userWithoutPlaceId;

    public void setUp() {
        userWithoutPlaceId = new UserWithoutPlaceId(userId, username, urlPicture, token);
    }

    public void testTestGetName() {
        assertTrue(userWithoutPlaceId.getUsername().equals(username));
    }

    public void testGetEmail() {
        assertTrue(userWithoutPlaceId.getUserId().equals(userId));
    }

    public void testGetUrlPicture() {
        assertTrue(userWithoutPlaceId.getUrlPicture().equals(urlPicture));
    }

    public void testGetToken() {
        assertTrue(userWithoutPlaceId.getToken().equals(token));
    }
}
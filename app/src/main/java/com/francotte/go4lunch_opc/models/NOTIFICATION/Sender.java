package com.francotte.go4lunch_opc.models.NOTIFICATION;

import java.util.List;

public class Sender {

    public final String data;
    public final List<String> registration_ids;

    public Sender(String data, List<String> registration_ids) {
        this.data = data;
        this.registration_ids = registration_ids;
    }
}

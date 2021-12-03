package com.francotte.go4lunch_opc.DI;

import android.content.Context;


public class InjectionMain {

    public static MainViewModelFactory provideViewModelFactory(Context context) {
        return new MainViewModelFactory(context);
    }
}

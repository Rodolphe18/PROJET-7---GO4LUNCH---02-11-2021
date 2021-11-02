package com.francotte.go4lunch_opc.DI;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.francotte.go4lunch_opc.viewmodel.MainViewModel;

import org.jetbrains.annotations.NotNull;


public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;
    MainViewModelFactory(Context context){
    this.context = context;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

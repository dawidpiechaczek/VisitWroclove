package com.example.dawid.visitwroclove;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Dawid on 23.07.2017.
 */

public class BaseActivity extends AppCompatActivity {
    private NonConfigurationComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = retriveInjectorOrCreateNew();
    }

    private NonConfigurationComponent retriveInjectorOrCreateNew() {
        Object lastNonConfInstance = getLastNonConfigurationInstance();
        if(lastNonConfInstance == null){
            MyApplication application = (MyApplication) getApplicationContext();
            return application.getAppComponent().nonConfigurationComponent();
        }else{
            return (NonConfigurationComponent) lastNonConfInstance;
        }
    }

    public NonConfigurationComponent getComponent() {
        return component;
    }
}

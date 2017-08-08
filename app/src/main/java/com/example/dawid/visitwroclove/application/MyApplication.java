package com.example.dawid.visitwroclove.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.example.dawid.visitwroclove.dagger.AppComponent;

import com.example.dawid.visitwroclove.dagger.AppModule;
import com.example.dawid.visitwroclove.dagger.DaggerAppComponent;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

/**
 * Created by Dawid on 17.07.2017.
 */

public class MyApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        stethoCongig();
        Realm.init(this);
        stethoInit();
        createAppComponent();
        appComponent.inject(this);
    }

    private void createAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void stethoInit() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }

    //This code protects before crashing stetho in Chrome plugin
    private void stethoCongig() {
        try {
            ComponentName componentName = new ComponentName(
                    getApplicationContext(),
                    "io.realm.internal.network.NetworkStateReceiver"
            );
            getPackageManager()
                    .setComponentEnabledSetting(
                            componentName,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
        } catch (Exception ignored) {
        }
    }
}

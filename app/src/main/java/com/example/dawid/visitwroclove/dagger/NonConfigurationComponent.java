package com.example.dawid.visitwroclove.dagger;

import com.example.dawid.visitwroclove.view.activity.DetailsActivity;
import com.example.dawid.visitwroclove.view.activity.EventsActivity;
import com.example.dawid.visitwroclove.view.activity.MainPanelActivity;
import com.example.dawid.visitwroclove.view.activity.MapActivity;
import com.example.dawid.visitwroclove.view.activity.PlacesActivity;
import com.example.dawid.visitwroclove.view.activity.RoutesActivity;
import com.example.dawid.visitwroclove.view.activity.RoutesListActivity;
import com.example.dawid.visitwroclove.view.activity.SplashScreenActivity;

import dagger.Subcomponent;

/**
 * Created by Dawid on 23.07.2017.
 */
@Subcomponent
@NonConfigurationScope
public interface NonConfigurationComponent {
    void inject(MainPanelActivity activity);
    void inject(MapActivity activity);
    void inject(DetailsActivity activity);
    void inject(PlacesActivity activity);
    void inject(EventsActivity activity);
    void inject(RoutesActivity activity);
    void inject(RoutesListActivity activity);
    void inject(SplashScreenActivity activity);
}

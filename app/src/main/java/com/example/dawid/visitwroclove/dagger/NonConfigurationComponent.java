package com.example.dawid.visitwroclove.dagger;

import com.example.dawid.visitwroclove.view.activity.DetailsActivity;
import com.example.dawid.visitwroclove.view.activity.EventsActivity;
import com.example.dawid.visitwroclove.view.activity.MainPanelActivity;
import com.example.dawid.visitwroclove.view.activity.MapActivity;
import com.example.dawid.visitwroclove.view.activity.PlacesActivity;

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
}

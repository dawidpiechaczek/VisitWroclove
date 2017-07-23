package com.example.dawid.visitwroclove;

import com.example.dawid.visitwroclove.view.activity.MainPanelActivity;
import com.example.dawid.visitwroclove.view.activity.MapActivity;

import dagger.Subcomponent;

/**
 * Created by Dawid on 23.07.2017.
 */
@Subcomponent
@NonConfigurationScope
public interface NonConfigurationComponent {
    void inject(MainPanelActivity activity);
    void inject(MapActivity activity);
}

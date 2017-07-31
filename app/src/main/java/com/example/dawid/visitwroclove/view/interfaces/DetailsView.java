package com.example.dawid.visitwroclove.view.interfaces;

import android.content.Intent;
import android.view.View;

/**
 * Created by Dawid on 24.07.2017.
 */

public interface DetailsView {
    View getImage();

    View getName();

    View getDescription();

    Intent getIntent();

    void setActionBarName(String name);
}

package com.example.dawid.visitwroclove.view.interfaces;

import com.akexorcist.googledirection.model.Direction;
import com.example.dawid.visitwroclove.model.BaseDTO;

/**
 * Created by Dawid on 24.07.2017.
 */

public interface MapView {
    void addMarker(BaseDTO baseDTO, String tag);
    void setCameraPosition(int position);
    void positiveRouteCallback(Direction direction);
    void negativeRouteCallback();
}

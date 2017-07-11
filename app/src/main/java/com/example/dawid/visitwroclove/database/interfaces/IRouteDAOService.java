package com.example.dawid.visitwroclove.database.interfaces;
import com.example.dawid.visitwroclove.model.RouteDTO;

import java.util.List;

/**
 * Created by Dawid on 07.07.2017.
 */

public interface IRouteDAOService {
    RouteDTO getByIdLocal(int id_local);
    List<RouteDTO> getByType(final String type);
    List<RouteDTO> getMine();
    void updateLocal(RouteDTO r);
}

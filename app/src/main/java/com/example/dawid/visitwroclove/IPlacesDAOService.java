package com.example.dawid.visitwroclove;

import java.util.List;

/**
 * Created by Dawid on 06.07.2017.
 */

public interface IPlacesDAOService extends IEntityDAOService<PlaceDTO>{
    List<PlaceDTO> getByType(final String type);
}

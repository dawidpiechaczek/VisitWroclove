package com.example.dawid.visitwroclove.database.interfaces;

import com.example.dawid.visitwroclove.model.EventDTO;

import java.util.List;

/**
 * Created by Dawid on 24.07.2017.
 */

public interface IEventDAOService extends IEntityDAOService<EventDTO>{
    List<EventDTO> getFavourites();
}
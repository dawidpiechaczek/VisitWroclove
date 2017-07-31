package com.example.dawid.visitwroclove.dagger;

import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dawid on 23.07.2017.
 */
@Module
public class DataBaseModule {
    @Provides
    @AppScope
    ObjectDAOImpl provideObjectDao(){
        return ObjectDAOImpl.getInstance();
    }

    @Provides
    @AppScope
    EventDAOImpl provideEventDao(){ return EventDAOImpl.getInstance(); }

}
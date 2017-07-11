package com.example.dawid.visitwroclove.database.utils;

/**
 * Created by Dawid on 11.07.2017.
 */

public interface RealmTable {

    String ID = "id";

    interface ObjectDAO {
        String NAME = "name";
        String TYPE = "type";
        String STANDARD = "standard";
        String RECOMMENDED = "recommended";
        String REMOVED = "removed";
        String STATUS = "status";
        String FAVOURITE = "isFavourite";

        interface AddressDAO {
            String CITY = "address.city";
        }

    }

    interface RoomDAO {
        String OBJECT_ID = "objectId";
    }

    interface RouteDAO {
        String NAME = "name";
        String REMOVED = "removed";
    }

    interface PlacesDAO{
        String TYPE = "type";
        String OBJECT_ID ="object_id";
    }

}
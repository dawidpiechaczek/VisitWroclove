package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.DAO.model.AddressDAO;
import com.example.dawid.visitwroclove.DAO.model.EventDAO;
import com.example.dawid.visitwroclove.database.enums.Removed;
import com.example.dawid.visitwroclove.database.enums.Status;
import com.example.dawid.visitwroclove.database.interfaces.IEventDAOService;
import com.example.dawid.visitwroclove.database.utils.AddressAssembler;
import com.example.dawid.visitwroclove.database.utils.RealmTable;
import com.example.dawid.visitwroclove.model.EventDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dawid on 24.07.2017.
 */

public class EventDAOImpl implements IEventDAOService {
    private static EventDAOImpl instance;

    public static EventDAOImpl getInstance(){
        if(instance == null){
            instance = new EventDAOImpl();
        }
        return instance;
    }

    protected static void setInstance(EventDAOImpl eventDAOImpl){
        instance = eventDAOImpl;
    }

    @Override
    public void add(EventDTO entity) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        EventDAO eDAO = new EventDAO();
        AddressDAO aDAO = new AddressDAO();

        aDAO.setId(entity.getAddress().getId());
        aDAO.setCity(entity.getAddress().getCity());
        aDAO.setStreet(entity.getAddress().getStreet());
        aDAO.setHomeNumber(entity.getAddress().getHomeNumber());
        aDAO.setZipCode(entity.getAddress().getZipCode());
        aDAO.setLat(entity.getAddress().getLat());
        aDAO.setLng(entity.getAddress().getLng());

        eDAO.setId(entity.getId());
        eDAO.setName(entity.getName());
        eDAO.setDescription(entity.getDescription());
        eDAO.setDate(entity.getStartDate());
        eDAO.setType(entity.getType());
        eDAO.setAddress_id(entity.getAddressId());
        eDAO.setAddressDAO(aDAO);
        eDAO.setImage(entity.getImage());
        eDAO.setRemoved(entity.getRemoved());
        eDAO.setPrice(entity.getPrice());
        eDAO.setFavourite(entity.isFavourite());
        eDAO.setWww(entity.getWww());

        realm.copyToRealmOrUpdate(eDAO);

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void deleteById(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        EventDAO e = realm.where(EventDAO.class).equalTo("id", id).findFirst();
        if (e != null) {
            e.deleteFromRealm();
        }

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public EventDTO getById(int id) {
        Realm realm = Realm.getDefaultInstance();
        EventDAO eDAO = realm.where(EventDAO.class)
                .equalTo("id", id)
                .equalTo(RealmTable.EventDAO.REMOVED, Removed.NOT_REMOVED.getValue())
//                .equalTo(RealmTable.EventDAO.STATUS, Status.PUBLISH.getValue())
                .findFirst();

        EventDTO eDTO = null;

        if (eDAO != null) {
            eDTO = new EventDTO();
            eDTO.setId(eDAO.getId());
            eDTO.setName(eDAO.getName());
            eDTO.setDescription(eDAO.getDescription());
            eDTO.setStartDate(eDAO.getDate());
            eDTO.setType(eDAO.getType());
            eDTO.setAddressId(eDAO.getAddress_id());
            eDTO.setAddress(AddressAssembler.AddressDAOtoDTO(eDAO.getAddressDAO()));
            eDTO.setImage(eDAO.getImage());
            eDTO.setRemoved(eDAO.getRemoved());
            eDTO.setPrice(eDAO.getPrice());
            eDTO.setFavourite(eDAO.isFavourite());
            eDTO.setWww(eDAO.getWww());
        }
        realm.close();
        return eDTO;
    }

    @Override
    public List<EventDTO> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<EventDAO> query = realm.where(EventDAO.class)
                .equalTo(RealmTable.EventDAO.REMOVED, Removed.NOT_REMOVED.getValue());
     //           .equalTo(RealmTable.EventDAO.STATUS, Status.PUBLISH.getValue());
        RealmResults<EventDAO> results = query.findAll().sort(RealmTable.EventDAO.START_DATE, Sort.ASCENDING);
        //RealmResults<EventDAO> results = query.findAll().sort(RealmTable.Report.ID, Sort.DESCENDING);

        List<EventDTO> list = new ArrayList<>();
        results.sort(RealmTable.ObjectDAO.NAME, Sort.ASCENDING);
        for (int i = 0; i < results.size(); i++) {

            EventDTO e = new EventDTO();
            e.setId(results.get(i).getId());
            e.setName(results.get(i).getName());
            e.setDescription(results.get(i).getDescription());
            e.setStartDate(results.get(i).getDate());
            e.setType(results.get(i).getType());
            e.setAddressId(results.get(i).getAddress_id());
            e.setAddress(AddressAssembler.AddressDAOtoDTO(results.get(i).getAddressDAO()));
            e.setImage(results.get(i).getImage());
            e.setRemoved(results.get(i).getRemoved());
            e.setPrice(results.get(i).getPrice());
            e.setFavourite(results.get(i).isFavourite());
            e.setWww(results.get(i).getWww());
            list.add(e);
        }

        realm.close();
        return list;
    }

    @Override
    public List<EventDTO> getFavourites() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<EventDAO> results = realm.where(EventDAO.class)
                .equalTo(RealmTable.EventDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo(RealmTable.EventDAO.STATUS, Status.PUBLISH.getValue())
                .equalTo(RealmTable.EventDAO.FAVOURITE, true).findAll().sort(RealmTable.EventDAO.START_DATE, Sort.ASCENDING);

        List<EventDTO> list = new ArrayList<>();

        if (results != null) {
            results.sort(RealmTable.ObjectDAO.NAME, Sort.ASCENDING);
            for (int i=0; i<results.size(); i++) {
                EventDTO e = new EventDTO();
                e.setId(results.get(i).getId());
                e.setName(results.get(i).getName());
                e.setDescription(results.get(i).getDescription());
                e.setStartDate(results.get(i).getDate());
                e.setType(results.get(i).getType());
                e.setAddressId(results.get(i).getAddress_id());
                e.setAddress(AddressAssembler.AddressDAOtoDTO(results.get(i).getAddressDAO()));
                e.setImage(results.get(i).getImage());
                e.setRemoved(results.get(i).getRemoved());
                e.setPrice(results.get(i).getPrice());
                e.setFavourite(results.get(i).isFavourite());
                e.setWww(results.get(i).getWww());
                list.add(e);
            }
        }

        realm.close();
        return list;
    }
}

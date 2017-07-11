package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.DAO.model.AddressDAO;
import com.example.dawid.visitwroclove.DAO.model.IEntityDAO;
import com.example.dawid.visitwroclove.model.AddressDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Dawid on 11.07.2017.
 */

public class AddressDAOImpl implements IEntityDAO<AddressDTO> {


    @Override
    public void add(AddressDTO entity) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        AddressDAO aDAO = new AddressDAO();
        aDAO.setId(entity.getId());
        aDAO.setCity(entity.getCity());
        aDAO.setZipCode(entity.getZipCode());
        aDAO.setStreet(entity.getStreet());
        aDAO.setHomeNumber(entity.getHomeNumber());
        aDAO.setLat(entity.getLat());
        aDAO.setLng(entity.getLng());

        realm.copyToRealmOrUpdate(aDAO);

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void deleteById(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        AddressDAO aDAO = realm.where(AddressDAO.class).equalTo("id", id).findFirst();
        if (aDAO != null) {
            aDAO.deleteFromRealm();
        }

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public AddressDTO getById(int id) {
        Realm realm = Realm.getDefaultInstance();
        AddressDAO aDAO = realm.where(AddressDAO.class).equalTo("id", id).findFirst();

        AddressDTO aDTO = null;

        if (aDAO != null) {
            aDTO = new AddressDTO();
            aDTO.setId(aDAO.getId());
            aDTO.setCity(aDAO.getCity());
            aDTO.setZipCode(aDAO.getZipCode());
            aDTO.setStreet(aDAO.getStreet());
            aDTO.setHomeNumber(aDAO.getHomeNumber());
            aDTO.setLat(aDAO.getLat());
            aDTO.setLng(aDAO.getLng());
        }

        realm.close();

        return aDTO;
    }

    @Override
    public List<AddressDTO> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<AddressDAO> query = realm.where(AddressDAO.class);
        RealmResults<AddressDAO> results = query.findAll();

        List<AddressDTO> list = new ArrayList<>();
        for (int i=0; i<results.size(); i++) {
            AddressDTO aDTO = new AddressDTO();
            aDTO.setId(results.get(i).getId());
            aDTO.setCity(results.get(i).getCity());
            aDTO.setZipCode(results.get(i).getZipCode());
            aDTO.setStreet(results.get(i).getStreet());
            aDTO.setHomeNumber(results.get(i).getHomeNumber());
            aDTO.setLat(results.get(i).getLat());
            aDTO.setLng(results.get(i).getLng());

            list.add(aDTO);
        }

        realm.close();

        return list;
    }
}

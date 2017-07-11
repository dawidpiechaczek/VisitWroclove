package com.example.dawid.visitwroclove.database.utils;

import com.example.dawid.visitwroclove.DAO.model.AddressDAO;
import com.example.dawid.visitwroclove.model.AddressDTO;

/**
 * Created by Dawid on 11.07.2017.
 */
public class AddressAssembler {

    public static AddressDTO AddressDAOtoDTO(AddressDAO addressDAO) {
        AddressDTO newDTO = new AddressDTO();

        newDTO.setId(addressDAO.getId());
        newDTO.setCity(addressDAO.getCity());
        newDTO.setStreet(addressDAO.getStreet());
        newDTO.setHomeNumber(addressDAO.getHomeNumber());
        newDTO.setZipCode(addressDAO.getZipCode());
        newDTO.setLat(addressDAO.getLat());
        newDTO.setLng(addressDAO.getLng());

        return newDTO;
    }
}
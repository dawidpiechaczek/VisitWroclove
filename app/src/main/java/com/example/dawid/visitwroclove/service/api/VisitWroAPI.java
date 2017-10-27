package com.example.dawid.visitwroclove.service.api;

import com.example.dawid.visitwroclove.model.AddressDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by DPIECHAC on 2017-10-25.
 */

public interface VisitWroAPI {

    String SERVICE_ENDPOINT = "http://visitwro.azurewebsites.net/api/";

    @GET("addresses/{addressId}")
    Observable<AddressDTO> getAddress(@Path("addressId") String addressId);

    @GET("places")
    Observable<List<ObjectDTO>> getObjects();

    @GET("events")
    Observable<List<EventDTO>> getEvents();

    @GET("addresses")
    Observable<List<AddressDTO>> getAddresses();

    class Factory {
        public static VisitWroAPI create() {
            return ServiceFactory.createRetrofitService(VisitWroAPI.class, SERVICE_ENDPOINT);
        }
    }
}

package com.example.dawid.visitwroclove.DAO.implementation;

import com.example.dawid.visitwroclove.DAO.model.PointDAO;
import com.example.dawid.visitwroclove.DAO.model.RouteDAO;
import com.example.dawid.visitwroclove.database.enums.Removed;
import com.example.dawid.visitwroclove.database.interfaces.IRouteDAOService;
import com.example.dawid.visitwroclove.database.utils.RealmTable;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dawid on 11.07.2017.
 */

public class RouteDAOImpl implements IRouteDAOService {

    private static final String TAG = RouteDAOImpl.class.getName();

    @Override
    public void add(RouteDTO entity) {
        int globalId;
        RouteDTO existingRoute = getById(entity.getId());

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        if (existingRoute != null) {
            globalId = existingRoute.getGlobalId();
        } else {
            if (realm.where(RouteDAO.class).max("globalId") != null) {
                globalId = realm.where(RouteDAO.class).max("globalId").intValue();
                globalId++;
            } else {
                globalId = 1;
            }
        }

        RouteDAO r = new RouteDAO();
        RealmList<PointDAO> points = new RealmList<>();

        r.setGlobalId(globalId);

        //int primaryKeyValue = new AtomicLong(realm.where(RouteDAO.class).max("id").longValue());
        int primaryKey;
        if (realm.where(RouteDAO.class).max("id_local") != null) {
            if (realm.where(RouteDAO.class).max("id_local").intValue() < 1) {
                primaryKey = 1;
            } else {
                primaryKey = realm.where(RouteDAO.class).max("id_local").intValue();
                primaryKey++;
            }
        } else {
            primaryKey = 1;
        }

        if (entity.isMine()) {
            r.setId(-1);
            r.setId_local(primaryKey);
        } else {
            r.setId(entity.getId());
            r.setId_local(-1);
        }

        //r.setId(entity.getId());
        //r.setId(primaryKey);
        r.setName(entity.getName());
        r.setDescription(entity.getDescription());
        r.setLength(entity.getLength());

        for (int i=0; i<entity.getPoints().size(); i++) {
            PointDAO p = new PointDAO();
            if (entity.getPoints().get(i).getId() != null)
                p.setId(entity.getPoints().get(i).getId());
            else
                p.setId(globalId + "." + i);
            p.setRouteId(entity.getPoints().get(i).getRouteId());
            p.setObjectId(entity.getPoints().get(i).getObjectId());
            p.setLat(entity.getPoints().get(i).getLat());
            p.setLng(entity.getPoints().get(i).getLng());
            p.setDescription(entity.getPoints().get(i).getDescription());

            points.add(p);
        }
        r.setPoints(points);
        r.setRemoved(entity.getRemoved());

        r.setMine(entity.isMine());

        realm.copyToRealmOrUpdate(r);

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void deleteById(int id_local) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RouteDAO e = realm.where(RouteDAO.class).equalTo("id_local", id_local).findFirst();
        if (e != null) {
            e.setRemoved(Removed.REMOVED.getValue());
        }

        realm.commitTransaction();
        realm.close();

    }

    @Override
    public RouteDTO getById(int id) {
        Realm realm = Realm.getDefaultInstance();
        RouteDAO rDAO = realm.where(RouteDAO.class)
                .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo("id", id).findFirst();

        RouteDTO r = null;

        if (rDAO != null) {
            r = new RouteDTO();
            r.setGlobalId(rDAO.getGlobalId());
            r.setId(rDAO.getId());
            r.setLength(rDAO.getLength());
            r.setDescription(rDAO.getDescription());
            r.setName(rDAO.getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int i=0; i<rDAO.getPoints().size(); i++) {
                PointDTO p = new PointDTO();
                p.setId(rDAO.getPoints().get(i).getId());
                p.setRouteId(rDAO.getPoints().get(i).getRouteId());
                p.setObjectId(rDAO.getPoints().get(i).getObjectId());
                p.setLat(rDAO.getPoints().get(i).getLat());
                p.setLng(rDAO.getPoints().get(i).getLng());
                p.setDescription(rDAO.getPoints().get(i).getDescription());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(rDAO.getId_local());
            r.setRemoved(rDAO.getRemoved());
        }

        realm.close();
        return r;
    }

    @Override
    public List<RouteDTO> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<RouteDAO> query = realm.where(RouteDAO.class)
                .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue());
        RealmResults<RouteDAO> results = query.findAll().sort(RealmTable.RouteDAO.NAME, Sort.ASCENDING);

        List<RouteDTO> list = new ArrayList<>();
        for (int i=0; i<results.size(); i++) {
            RouteDTO r = new RouteDTO();
            r.setId(results.get(i).getId());
            r.setLength(results.get(i).getLength());
            r.setDescription(results.get(i).getDescription());
            r.setName(results.get(i).getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int j=0; j<results.get(i).getPoints().size(); j++) {
                PointDTO p = new PointDTO();

                p.setId(results.get(i).getPoints().get(j).getId());
                p.setRouteId(results.get(i).getPoints().get(j).getRouteId());
                p.setObjectId(results.get(i).getPoints().get(j).getObjectId());
                p.setLat(results.get(i).getPoints().get(j).getLat());
                p.setLng(results.get(i).getPoints().get(j).getLng());
                p.setDescription(results.get(i).getPoints().get(j).getDescription());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(results.get(i).getId_local());
            r.setRemoved(results.get(i).getRemoved());

            list.add(r);
        }

        realm.close();
        return list;
    }

    // =============================================================================================

    @Override
    public RouteDTO getByIdLocal(int id_local) {
        Realm realm = Realm.getDefaultInstance();
        RouteDAO rDAO = realm.where(RouteDAO.class)
                .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo("id_local", id_local).findFirst();

        RouteDTO r = null;

        if (rDAO != null) {
            r = new RouteDTO();
            r.setId(rDAO.getId());
            r.setLength(rDAO.getLength());
            r.setDescription(rDAO.getDescription());
            r.setName(rDAO.getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int i=0; i<rDAO.getPoints().size(); i++) {
                PointDTO p = new PointDTO();
                p.setRouteId(rDAO.getPoints().get(i).getRouteId());
                p.setObjectId(rDAO.getPoints().get(i).getObjectId());
                p.setLat(rDAO.getPoints().get(i).getLat());
                p.setLng(rDAO.getPoints().get(i).getLng());
                p.setDescription(rDAO.getPoints().get(i).getDescription());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(rDAO.getId_local());
            r.setRemoved(rDAO.getRemoved());
        }

        realm.close();
        return r;
    }

    @Override
    public List<RouteDTO> getByType(String type) {
        return null;
    }

    @Override
    public List<RouteDTO> getMine() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<RouteDAO> query = realm.where(RouteDAO.class)
                .equalTo(RealmTable.RouteDAO.REMOVED, Removed.NOT_REMOVED.getValue())
                .equalTo("isMine", true);
        RealmResults<RouteDAO> results = query.findAll().sort(RealmTable.RouteDAO.NAME, Sort.ASCENDING);

        List<RouteDTO> list = new ArrayList<>();
        for (int i=0; i<results.size(); i++) {
            RouteDTO r = new RouteDTO();
            r.setId(results.get(i).getId());
            r.setLength(results.get(i).getLength());
            r.setDescription(results.get(i).getDescription());
            r.setName(results.get(i).getName());

            ArrayList<PointDTO> points = new ArrayList<>();
            for (int j=0; j<results.get(i).getPoints().size(); j++) {
                PointDTO p = new PointDTO();

                p.setRouteId(results.get(i).getPoints().get(j).getRouteId());
                p.setObjectId(results.get(i).getPoints().get(j).getObjectId());
                p.setLat(results.get(i).getPoints().get(j).getLat());
                p.setLng(results.get(i).getPoints().get(j).getLng());
                p.setDescription(results.get(i).getPoints().get(j).getDescription());

                points.add(p);
            }
            r.setPoints(points);
            r.setId_local(results.get(i).getId_local());
            r.setMine(results.get(i).isMine());

            list.add(r);
        }

        realm.close();
        return list;
    }

    @Override
    public void updateLocal(RouteDTO r) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RouteDAO fromDbDAO = realm.where(RouteDAO.class).equalTo("id_local", r.getId_local()).findFirst();

        RouteDAO rDAO = new RouteDAO();
        if (fromDbDAO != null) {
            rDAO.setGlobalId(fromDbDAO.getGlobalId());
            rDAO.setId(-1);
            rDAO.setName(r.getName());
            rDAO.setDescription(r.getDescription());
            rDAO.setLength(r.getLength());

            RealmList<PointDAO> points = new RealmList<>();
            for (int i=0; i<r.getPoints().size(); i++) {
                PointDAO p = new PointDAO();
                p.setId(fromDbDAO.getGlobalId() + "." + i);
                p.setRouteId(r.getPoints().get(i).getRouteId());
                p.setObjectId(r.getPoints().get(i).getObjectId());
                p.setLat(r.getPoints().get(i).getLat());
                p.setLng(r.getPoints().get(i).getLng());
                p.setDescription(r.getPoints().get(i).getDescription());

                points.add(p);
            }
            rDAO.setPoints(points);

            rDAO.setId_local(r.getId_local());
            rDAO.setMine(true);
        }

        realm.copyToRealmOrUpdate(rDAO);

        realm.commitTransaction();
        realm.close();
    }

}

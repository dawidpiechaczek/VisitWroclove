package com.example.dawid.visitwroclove.enums;

/**
 * Created by Dawid on 10.08.2017.
 */

public enum Categories {
    FOREST("leśne zacisze"),

    WATER("przy rzece"),

    CYCLE("rowerowa przygoda"),

    COOK("dla smakosza"),

    WALKING("pieszy relaks"),

    FAVOURITE("ulubiona");

    private final String constValue;

    Categories(String constValue) {
        this.constValue = constValue;
    }

    public String getValue() {
        return constValue;
    }

}

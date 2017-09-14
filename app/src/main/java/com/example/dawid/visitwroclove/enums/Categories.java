package com.example.dawid.visitwroclove.enums;

/**
 * Created by Dawid on 10.08.2017.
 */

public enum Categories {
    FOREST("leśna"),

    WATER("morska"),

    CYCLE("rowerowa"),

    COOK("dla smakosza"),

    WALKING("piesza"),

    FAVOURITE("ulubiona");

    private final String constValue;

    Categories(String constValue) {
        this.constValue = constValue;
    }

    public String getValue() {
        return constValue;
    }

}

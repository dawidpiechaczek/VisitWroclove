package com.example.dawid.visitwroclove.enums;

/**
 * Created by Dawid on 10.08.2017.
 */

public enum Categories {
    LAS(0),

    WODA(1),

    ROWER(2);

    private final int constValue;

    Categories(int constValue) {
        this.constValue = constValue;
    }

    public int getValue() {
        return constValue;
    }

}

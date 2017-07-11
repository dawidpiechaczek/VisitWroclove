package com.example.dawid.visitwroclove.database.enums;

/**
 * Created by Dawid on 11.07.2017.
 */

public enum Removed {

    NOT_REMOVED(0),
    REMOVED(1);

    private final int constValue;

    Removed(int constValue) {
        this.constValue = constValue;
    }

    public int getValue(){
        return constValue;
    }

}
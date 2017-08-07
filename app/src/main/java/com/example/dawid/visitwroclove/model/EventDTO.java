package com.example.dawid.visitwroclove.model;

/**
 * Created by Dawid on 24.07.2017.
 */

public class EventDTO extends BaseDTO {

    private String date;
    private int removed;
    private String price;
    private String www;

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getStartDate() {
        return date;
    }

    public void setStartDate(String date) {
        this.date = date;
    }

    public int getRemoved() {
        return removed;
    }

    public void setRemoved(int removed) {
        this.removed = removed;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

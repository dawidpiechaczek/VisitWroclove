package com.example.dawid.visitwroclove.model;

/**
 * Created by Dawid on 07.07.2017.
 */

public class ObjectDTO {

    private int id;
    private String type;
    private String name;
    private String description;
    private String phone;
    private double rank;
    private int standard;
    private int address_id;
    private AddressDTO address;
    private String thumb;
    private String image;
    private int removed;
    private boolean isFavourite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public int getAddressId() {
        return address_id;
    }

    public void setAddressId(int addressId) {
        this.address_id = addressId;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int isRemoved() {
        return removed;
    }

    public void setRemoved(int removed) {
        this.removed = removed;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

 //   @Override
 //   public String toString() {
 //       return ToStringBuilder.reflectionToString(this);
 //   }
}

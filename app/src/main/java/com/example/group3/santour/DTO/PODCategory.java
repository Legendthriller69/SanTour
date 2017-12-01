package com.example.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by aleks on 21.11.2017.
 */

@IgnoreExtraProperties
public class PODCategory implements Serializable {

    private String idCategory;
    private int value;

    public PODCategory() {

    }

    public PODCategory(String idCategory, int value) {
        this.idCategory = idCategory;
        this.value = value;
    }

    public PODCategory(int value) {
        this.value = value;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PODCategory{" +
                "idCategory='" + idCategory + '\'' +
                ", value=" + value +
                '}';
    }
}

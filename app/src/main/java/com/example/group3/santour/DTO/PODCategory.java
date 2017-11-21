package com.example.group3.santour.DTO;

/**
 * Created by aleks on 21.11.2017.
 */

public class PODCategory {

    private String idPOD;
    private String idCategory;
    private int value;

    public PODCategory() {

    }

    public PODCategory(String idPOD, String idCategory, int value) {
        this.idPOD = idPOD;
        this.idCategory = idCategory;
        this.value = value;
    }

    public String getIdPOD() {
        return idPOD;
    }

    public void setIdPOD(String idPOD) {
        this.idPOD = idPOD;
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
}

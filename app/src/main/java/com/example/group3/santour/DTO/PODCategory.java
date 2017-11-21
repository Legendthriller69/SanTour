package com.example.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by aleks on 21.11.2017.
 */

@IgnoreExtraProperties
public class PODCategory {

    private POD pod;
    private Category category;
    private int value;

    public PODCategory() {

    }

    public PODCategory(POD pod, Category category, int value) {
        this.pod = pod;
        this.category = category;
        this.value = value;
    }

    public POD getPod() {
        return pod;
    }

    public void setPod(POD pod) {
        this.pod = pod;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

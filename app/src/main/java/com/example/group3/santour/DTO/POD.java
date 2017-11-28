package com.example.group3.santour.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleks on 21.11.2017.
 */

@IgnoreExtraProperties
public class POD extends Point implements Serializable {

    private List<PODCategory> podCategories;

    public POD() {
        super();
        podCategories = new ArrayList<>();
    }

    public POD(String id, String name, String picture, String description, Position position, List<PODCategory> podCategories) {
        super(id, name, picture, description, position);
        this.podCategories = podCategories;
    }

    public POD(String name, String picture, String description, Position position, List<PODCategory> podCategories) {
        super(name, picture, description, position);
        this.podCategories = podCategories;
    }

    public List<PODCategory> getPodCategories() {
        return podCategories;
    }

    public void setPodCategories(List<PODCategory> podCategories) {
        this.podCategories = podCategories;
    }

    @Override
    public String toString() {
        return super.toString() + "\n POD{" +
                "podCategories=" + podCategories +
                '}';
    }
}

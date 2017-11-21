package com.example.group3.santour.DTO;

import java.util.Map;

/**
 * Created by aleks on 21.11.2017.
 */

public class POD extends Point {

    private Map<POD, Integer> PODCategories;

    public POD() {
    }

    public POD(String id, String name, String picture, String description, String idPosition, String idTrack) {
        super(id, name, picture, description, idPosition, idTrack);
    }

    @Override
    public String toString() {
        return "POD : ";
    }

    public Map<POD, Integer> getPODCategories() {
        return PODCategories;
    }

    public void setPODCategories(Map<POD, Integer> PODCategories) {
        this.PODCategories = PODCategories;
    }
}

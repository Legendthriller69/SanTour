package com.example.group3.santour.DTO;

import java.util.Map;

/**
 * Created by aleks on 21.11.2017.
 */

public class POD extends Point {

    public POD() {
    }

    public POD(String id, String name, String picture, String description, String idPosition, String idTrack) {
        super(id, name, picture, description, idPosition, idTrack);
    }

    @Override
    public String toString() {
        return "POD : ";
    }

}

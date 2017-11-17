package com.example.group3.santour.DTO;

/**
 * Created by DarkFace on 17 nov. 2017.
 */

public class Track {

        private String id;
        private String name;
        private String description;
        private double distance;
        private int duration;
        private Type type;


        public Track(){

        }

        public Track(String name, String description, double distance, int duration, Type type){

            this.name = name;
            this.description = description;
            this.distance = distance;
            this.duration = duration;
            this.type = type;
        }

        //getter
        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getDescription() {
            return this.description;
        }

        public double getDistance() {
            return this.distance;
        }

        public int getDuration() {
            return this.duration;
        }

        public Type getType() {
            return this.type;
        }


        public void setId(String id) {
                this.id=id;
        }
}

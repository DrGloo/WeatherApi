package com.example.demo.model;

import java.util.List;

public class CoordinatesResponseModel {
    List<LocationModel> results;

    public List<LocationModel> getResults() {
        return results;
    }

    public void setResults(List<LocationModel> results) {
        this.results = results;
    }

    public class LocationModel{
        List<CoordinateWrapperModel> locations;

        public List<CoordinateWrapperModel> getLocations() {
            return locations;
        }

        public void setLocations(List<CoordinateWrapperModel> locations) {
            this.locations = locations;
        }
    }

    public class CoordinateWrapperModel{
        private CoordinateModel latLng;

        public CoordinateModel getLatLng() {
            return latLng;
        }

        public void setLatLng(CoordinateModel latLng) {
            this.latLng = latLng;
        }
    }

    public class CoordinateModel{
        private Float lat;
        private Float lon;

        public Float getLat() {
            return lat;
        }

        public void setLat(Float lat) {
            this.lat = lat;
        }

        public Float getLon() {
            return lon;
        }

        public void setLon(Float lon) {
            this.lon = lon;
        }
    }

}

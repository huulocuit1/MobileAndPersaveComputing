package entities;

/**
 * Created by HL_TH on 12/19/2016.
 */

public class MyData {

    private int id;
    private String District;
    private double Lat;
    private double Lng;

    public MyData(String district, double lng, double lat, int id) {
        District = district;
        Lng = lng;
        Lat = lat;
        this.id = id;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }
}

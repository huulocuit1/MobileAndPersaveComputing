package server;
import java.util.ArrayList;
import java.util.List;

import entities.Atm;
import entities.Bank;
import entities.District;


public class DataManager {
    private static DataManager instance;

    private  ArrayList<Atm> atmDetails;
    private List<Bank> bankDetails;
    private List<Atm> searchDetails;

    public List<Atm> getAtmByType() {
        return atmByType;
    }

    public void setAtmByType(List<Atm> atmByType) {
        this.atmByType = atmByType;
    }

    private List<Atm> atmByType;
    private List<District> districtDetails;

    public List<District> getDistrictDetails() {
        return districtDetails;
    }

    public void setDistrictDetails(List<District> districtDetails) {
        this.districtDetails = districtDetails;
    }

    private String wayJson;

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    private DataManager() {

    }

    /* ------------------------------------------- */

    public void setAtmDetail(ArrayList<Atm> atmDetail) {
        atmDetails = atmDetail;
    }

    public ArrayList<Atm> getAtmDetail() {
        return atmDetails;
    }

    /* ------------------------------------------- */

    public void setBankDetail(List<Bank> bankDetail) {
        bankDetails = bankDetail;
    }

    public List<Bank> getBankDetail() {
        return bankDetails;
    }

    /* ------------------------------------------- */

    public void setWayJson(String json) {
        wayJson = json;
    }

    public String getWayJson() {
        return wayJson;
    }


    /* ------------------------------------------- */

    public void setSearchDetail(List<Atm> atmDetail) {
        searchDetails = atmDetail;
    }

    public List<Atm> getSearchDetail() {
        return searchDetails;
    }
}

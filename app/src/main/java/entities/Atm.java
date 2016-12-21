package entities;

import org.json.JSONObject;

/**
 * Created by MinhNhan on 27/04/2016.
 * Parse AtmDetail JsonObject to Atm
 */
public class Atm {

    public final String BannkType = "BankType";
    public final String District = "District";
    public final String PlaceName = "PlaceName";
    public final String Address = "Address";
    public final String LAT = "Latitude";
    public final String LNG = "Longitude";


    private String banktype;
    private String district;
    private String placename;
    private String address;
    private double lat;
    private double lng;

    public String getAddress() {
        return address;
    }

    public String getBanktype() {
        return banktype;
    }

    public String getDistrict() {
        return district;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getPlacename() {
        return placename;
    }

    public Atm(JSONObject object) {
        try {
            if (object.has(BannkType))
                banktype = object.getString(BannkType);
            if (object.has(District))
                district = object.getString(District);
            if (object.has(PlaceName))
                placename = object.getString(PlaceName);
            if (object.has(Address))
                address = object.getString(Address);
            if (object.has(LAT))
                lat = Double.parseDouble(object.getString(LAT));
            if (object.has(LNG))
                lng = Double.parseDouble(object.getString(LNG));

        } catch (Exception e) {
        }
    }
}

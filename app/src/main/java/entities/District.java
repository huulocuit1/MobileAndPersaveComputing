package entities;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HL_TH on 12/22/2016.
 */

public class District {
    public final String District = "District";
    private ArrayList<String> listBankName;

    public District(ArrayList<String> listBankName) {
        this.listBankName = listBankName;
    }


    public String name;

    public District(JSONObject object) {
        try {
            if (object.has(District))
                name = object.getString(District);
        } catch (Exception e) {
        }
    }
}

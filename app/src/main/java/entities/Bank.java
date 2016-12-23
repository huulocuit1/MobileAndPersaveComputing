package entities;

import org.json.JSONObject;

import java.util.ArrayList;


public class Bank {
    public final String BannkType = "BankType";
    private ArrayList<String> listBankName;

    public Bank(ArrayList<String> listBankName) {
        this.listBankName = listBankName;
    }


    public String name;

    public Bank(JSONObject object) {
        try {
            if (object.has(BannkType))
                name = object.getString(BannkType);
        } catch (Exception e) {
        }
    }
}

package entities;

import android.util.Base64;

import org.json.JSONObject;


public class Bank {
    public final String ID  = "Id";
    public final String NAME = "Name";

    public long id;
    public String name;

    public Bank(JSONObject object) {
        try {
            if (object.has(ID))
                id = Long.parseLong(object.getString(ID));
            if (object.has(NAME))
                name = object.getString(NAME);
        } catch (Exception e) {
        }
    }

    private String decodeUrl(String decodeUrl) {
        byte[] bytes = Base64.decode(decodeUrl.substring(11), Base64.DEFAULT);
        return new String(bytes);
    }
}

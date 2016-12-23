package server;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.District;

/**
 * Created by HL_TH on 12/22/2016.
 */

public class AsynDistrict extends AsyncTask<String, String, String> {

    private AsyncListener listener;

    public AsynDistrict(AsyncListener listener) {
        this.listener = listener;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            return DataServices.getRequest(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("ATM");
            List<District> results = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                District district = new District(jsonArray.getJSONObject(i));
                results.add(district);
            }
            DataManager.getInstance().setDistrictDetails(results);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listener != null) {
            try {
                listener.onAsyncComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

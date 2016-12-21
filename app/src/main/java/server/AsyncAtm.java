package server;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import entities.Atm;

public class AsyncAtm extends AsyncTask<String, String, String> {
    private AsyncListener listener;



    public AsyncAtm(AsyncListener listener) {
        this.listener = listener;
    }



    @Override
    protected String doInBackground(String... params) {
        String rs = "";
        try {
            rs = DataServices.getRequest(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("ATM");
            ArrayList<Atm> results = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Atm atm = new Atm(jsonArray.getJSONObject(i));

                results.add(atm);
            }
            DataManager.getInstance().setAtmDetail(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listener != null) {
            listener.onAsyncComplete();
        }
    }
}
package server;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Bank;


public class AsyncBank  extends AsyncTask<String, String, String> {
    private AsyncListener listener;

    public AsyncBank(AsyncListener listener) {
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
            List<Bank> results = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Bank atm = new Bank(jsonArray.getJSONObject(i));
                results.add(atm);
            }
            DataManager.getInstance().setBankDetail(results);

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
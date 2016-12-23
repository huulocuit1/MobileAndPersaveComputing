package utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HL_TH on 12/23/2016.
 */

public class GetDirection extends AsyncTask<String, String, String> {
    Activity activity;
    Dialog dialog;
    LatLng origin;
    LatLng destination;
    List<LatLng> pontos;
    GoogleMap googleMap;

    public  GetDirection(GoogleMap googleMap, Activity activity, LatLng origin, LatLng destination)
    {
        this.activity=activity;
        this.origin=origin;
        this.destination=destination;
        pontos=new ArrayList<>();
        this.googleMap=googleMap;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... args) {
        String stringUrl = DrawWay.getDirectionsUrl(this.origin,this.destination);
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection httpconn = (HttpURLConnection) url
                    .openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(httpconn.getInputStream()),
                        8192);
                String strLine = null;

                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();
            }

            String jsonOutput = response.toString();

            JSONObject jsonObject = new JSONObject(jsonOutput);

            // routesArray contains ALL routes
            JSONArray routesArray = jsonObject.getJSONArray("routes");
            // Grab the first route
            JSONObject route = routesArray.getJSONObject(0);

            JSONObject poly = route.getJSONObject("overview_polyline");
            String polyline = poly.getString("points");
            return polyline;
        } catch (Exception e) {

        }
        return null;
    }

    protected void onPostExecute(String polyline) {
        Polyline line;
        List<LatLng> list = DrawWay.decodePoly(polyline);
        //Draw the Path line
        PolylineOptions options = new PolylineOptions().width(10).color(Color.rgb(0,179,253)).geodesic(true);
        for (int z = 0; z < list.size(); z++) {
            LatLng point = list.get(z);
            options.add(point);
        }
        line = googleMap.addPolyline(options);
        line.setColor(Color.RED);
        line.setWidth(15);
//        dialog.dismiss();

    }

}
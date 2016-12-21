package utils;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by HL_TH on 12/4/2016.
 */

public class LocationUtils {

    private Activity activity;
    LocationManager locationManager;
    public static String provider;

    public LocationUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     *
     * @return tên của nhà cung cấp dịch vụ như GPS
     */
    public String getBestLocationProvider() {
        locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);

        if (locationManager.isProviderEnabled(provider) != true) {
            Toast.makeText(activity, "No location provider enabled!", Toast.LENGTH_LONG).show();
            return null;
        }
        return provider;

    }

    /**
     *
     * @return trạng thái của GPS hiện tại của điện thoại
     */
    public boolean checkGPS()
    {
        LocationManager locationManager= (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        boolean status = locationManager.isProviderEnabled("gps");
        return status;
    }

    /**
     *
     * @return Điện thoại chưa kết nối với Internet
     */

    public boolean checkInternetConnection() {

        ConnectivityManager connManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(activity, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(activity, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(activity, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(activity, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

}




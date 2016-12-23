package utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


public class Utils implements LocationListener {
    public static Activity activity;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static String getDistrict(String district) {
        if (district.equals("Thủ Đức")) {
            district = "ThuDuc";
        } else if (district.equals("Dĩ An")) {
            district = "ThuDuc";
        } else if (district.equals("Gò Vấp")) {
            district = "GoVap";
        } else if (district.equals("Bình Thạnh")) {
            district = "BinhThanh";
        } else if (district.equals("Tân Bình")) {
            district = "TanBinh";
        } else if (district.equals("Tân Phú")) {
            district = "TanPhu";
        } else if (district.equals("Phú Nhuận")) {
            district = "PhuNhuan";
        } else if (district.equals("Bình Tân")) {
            district = "BinhTan";
        } else if (district.equals("Củ Chi")) {
            district = "CuChi";
        } else if (district.equals("Hóc Mộn")) {
            district = "HocMon";
        } else if (district.equals("Bình Chánh")) {
            district = "BinhChanh";
        } else if (district.equals("Nhà Bè")) {
            district = "NhaBe";
        } else if (district.equals("Cần Giờ")) {
            district = "CanGio";
        } else if (district.contains("Quận"))
            district = district.replace("Quận ", "");
        else district.replace("District ", " ");

        return district;
    }

    public static boolean isGPSEnabled(Context context) {
        LocationManager lm;
        boolean gps_enabled = false;
        try {
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gps_enabled;
    }

    public static boolean checkJps() {
        int v = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

            // Các quyền cần người dùng cho phép.
            String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION};

            // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
            ActivityCompat.requestPermissions(activity, permissions,
                    1234);
        }
        boolean isgps = isGPSEnabled(activity);
        return isgps;
    }

    public String getCurrentDistrict() {
        String provider = "";
        LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);

        if (locationManager.isProviderEnabled(provider) != true) {
            Toast.makeText(activity, "No location provider enabled!", Toast.LENGTH_LONG).show();
            return null;
        }
        // Millisecond
        final long MIN_TIME_BW_UPDATES = 10000;
        // Met
        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

        Location myLocation = null;
        try {


            locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            // Lấy ra vị trí.
            myLocation = locationManager.getLastKnownLocation(provider);
            if(myLocation==null)
            {
                myLocation=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            String s=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).toString();


        }
        // Với Android API >= 23 phải catch SecurityException.
        catch (SecurityException e) {
            Toast.makeText(activity, "Show My Location Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }

        List<Address> list = new ArrayList<>();
        Geocoder geocoder;
        geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            list = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = list.get(0);

        String str=address.getSubAdminArea();

        return str;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void getAllBankFromServer() {

    }
}

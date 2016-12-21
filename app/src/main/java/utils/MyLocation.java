package utils;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by HL_TH on 12/4/2016.
 */

public class        MyLocation implements LocationListener {


    private GoogleMap googleMap;

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    Location myLocation;
    private  Activity activity;
    LocationUtils locationUtils;

    public MyLocation(Activity activity, GoogleMap googleMap, LocationUtils locationUtils) {
        this.activity = activity;
        this.googleMap = googleMap;
        this.locationUtils= locationUtils;
    }
    //    Gọi khi mà location thay đổi
    @Override
    public void onLocationChanged(Location location) {

    }

// Gọi khi có sự thay đổi Provider
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
//Gọi khi Provider được bật trở lại bởi user
    @Override
    public void onProviderEnabled(String s) {

    }
    // Gọi khi provider bị disable bới user
    @Override
    public void onProviderDisabled(String s) {

    }
    public void showMyLocation() throws IOException {
        LocationManager locationManager = (LocationManager)activity.getSystemService(LOCATION_SERVICE);
        String locationProvider=locationUtils.getBestLocationProvider();
        if(locationManager==null)
            return;
        // Millisecond
        final long MIN_TIME_BW_UPDATES = 5000;
        // Met
        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

       myLocation = null;
        try {

            locationManager.requestLocationUpdates(locationProvider,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            // Lấy ra vị trí.
            myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);



        }
        // Với Android API >= 23 phải catch SecurityException.
        catch (SecurityException e) {
            Toast.makeText(activity, "Show My Location Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

            e.printStackTrace();
            return;
        }

        if (myLocation != null) {

            List<Address> list = new ArrayList<>();
            Geocoder geocoder;
            geocoder = new Geocoder(activity, Locale.getDefault());
            list = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
            Address address = list.get(0);
//            Vẽ vị trí tìm được
            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)             // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // Thêm Marker cho Map:
            MarkerOptions option = new MarkerOptions();
            option.title("'Your Location");
            option.snippet(address.getAddressLine(0)+","+address.getAddressLine(1)+","+address.getAddressLine(2));
            option.position(latLng);
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            Marker currentMarker =googleMap.addMarker(option);
            currentMarker.showInfoWindow();
        } else {
            Toast.makeText(activity, "Location not found!", Toast.LENGTH_LONG).show();

        }

    }


}

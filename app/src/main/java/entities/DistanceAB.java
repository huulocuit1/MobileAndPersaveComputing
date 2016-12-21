package entities;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by MinhNhan on 17/05/2016.
 */
public class DistanceAB {
    public static double distance(LatLng a, LatLng b) {
        Location locationA = new Location("locationA");
        locationA.setLatitude(a.latitude);
        locationA.setLongitude(a.longitude);
        Location locationB = new Location("locationB");
        locationB.setLatitude(b.latitude);
        locationB.setLongitude(b.longitude);
        double distance = locationA.distanceTo(locationB);
        return distance;
    }
}

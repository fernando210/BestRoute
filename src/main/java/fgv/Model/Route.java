package fgv.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 28/11/2017.
 */

public class Route {
    private final List<LatLng> points;
    private String polyline;

    public Route() {
        points = new ArrayList<LatLng>();
    }

    public void addPoints(final List<LatLng> points) {
        this.points.addAll(points);
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public String getPolyline() {
        return polyline;
    }
}

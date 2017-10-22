package com.tsystems.app.logistics.utils;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodedWaypointStatus;
import com.google.maps.model.LatLng;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GeoUtils {
    private static final Logger LOG = LogManager.getLogger(GeoUtils.class);

    private final String googleApiKey = "AIzaSyCAM6Ao-vEfShmZaDDhLDfpfXgDtZMfYv4";

    /**
     * Calculate distance between start and finish points through specified intermediate points
     * using Google Maps Directions API
     *
     * @param start coordinates of start way point
     * @param finish coordinates of finish way point
     * @param waypoints array of intermediate way points coordinates
     * @return distance in meters
     */
    public Long getDistanceBetweenWithGoogleApi(LatLng start, LatLng finish, LatLng... waypoints) {
        GeoApiContext.Builder builder = new GeoApiContext.Builder();
        builder.apiKey(googleApiKey);
        GeoApiContext context = builder.build();

        DirectionsResult result;
        Long distance = 0L;
        try {
            result = DirectionsApi.newRequest(context).origin(start).destination(finish).waypoints(waypoints).await();
            if (!Objects.equals(result.geocodedWaypoints[0].geocoderStatus, GeocodedWaypointStatus.OK)) {
                throw new RuntimeException("Incorrect response status on distance request");
            }
            for (int i = 0; i < result.routes[0].legs.length; i++) {
                distance += result.routes[0].legs[i].distance.inMeters;
            }
            return distance;
        } catch (Exception e) {
            LOG.error("Google maps error. Cannot calculate distance");
            return 0L;
        }
    }
}

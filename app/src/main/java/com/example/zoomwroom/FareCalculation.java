package com.example.zoomwroom;

import com.google.android.gms.maps.model.Marker;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FareCalculation {

    /**
     * gets the recommended fare price using formula baseprice + multiplier * distance between points
     *
     * @param basePrice
     * @param multiplier
     * @param depart
     * @param dest
     * @return price
     */
    public static double getPrice(double basePrice, double multiplier, Marker depart, Marker dest) {
        if(depart.isVisible() && dest.isVisible()) {
            double price = basePrice + multiplier *
                    getDistance(depart.getPosition().latitude,
                            dest.getPosition().latitude,
                            depart.getPosition().longitude,
                            dest.getPosition().longitude);
            return round(price, 2);
        } else {
            return 0;
        }
    }

    /**
     * gets distance in kilometers from two latitude/longitude points
     * by using the haversine formula : https://www.movable-type.co.uk/scripts/latlong.html
     *
     * Adapted from javascript code
     * @return distance
     */
    public static double getDistance(double lat1, double lat2, double long1, double long2) {
        final int R = 6371; // Radius of the earth in Km
        Double latDistance = toRad(lat1 - lat2);
        Double lonDistance = toRad(long1
                - long2);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) *
                        Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    /**
     * Rounds a double value to int places
     * Source: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     * @param value
     * @param places
     * @return roundedNum
     */
    protected static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Converts a degree to a radian value
     * @param value
     * @return radian
     */
    static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}


package com.caterpillar.platform.pc2repasoswagger.shared.domain.model.valueobjects;

/**
 * Geographic coordinate whose constructor protects latitude and longitude ranges.
 *
 * @param latitude latitude from -90 to 90
 * @param longitude longitude from -180 to 180
 * @author Gabriel Yanac Flores
 */
public record GeoCoordinate(double latitude, double longitude) {

    public GeoCoordinate {
        if (!Double.isFinite(latitude) || latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (!Double.isFinite(longitude) || longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }
}

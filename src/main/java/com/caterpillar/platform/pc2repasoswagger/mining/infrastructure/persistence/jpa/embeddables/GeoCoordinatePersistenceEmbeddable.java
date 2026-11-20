package com.caterpillar.platform.pc2repasoswagger.mining.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * JPA representation of a geographic coordinate.
 *
 * @author Gabriel Yanac Flores
 */
@Embeddable
public class GeoCoordinatePersistenceEmbeddable {

    @Column(name = "gps_latitude", nullable = false)
    private double latitude;

    @Column(name = "gps_longitude", nullable = false)
    private double longitude;

    protected GeoCoordinatePersistenceEmbeddable() { }

    public GeoCoordinatePersistenceEmbeddable(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}

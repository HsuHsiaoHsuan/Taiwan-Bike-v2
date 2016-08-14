package idv.funnybrain.bike.data;

/**
 * Created by freeman on 8/13/16.
 */

public class Station {
    private final String id;
    private final String name;
    private final String address;
    private final double lat;
    private final double lon;
    private final int bike;
    private final int park;
    private final int alive;

    public Station(String id, String name, String address, double lat, double lon, int bike, int park, int alive) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.bike = bike;
        this.park = park;
        this.alive = alive;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getBike() {
        return bike;
    }

    public int getPark() {
        return park;
    }

    public int getAlive() {
        return alive;
    }
}

package idv.funnybrain.bike.data;

/**
 * Created by freeman on 8/13/16.
 */

public class Station {
    private String id;
    private String name;
    private String address;
    private double lat;
    private double lon;
    private int bike;
    private int park;
    private int alive;

    public Station() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getBike() {
        return bike;
    }

    public void setBike(int bike) {
        this.bike = bike;
    }

    public int getPark() {
        return park;
    }

    public void setPark(int park) {
        this.park = park;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}

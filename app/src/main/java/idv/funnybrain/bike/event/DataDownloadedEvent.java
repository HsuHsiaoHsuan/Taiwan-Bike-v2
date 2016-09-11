package idv.funnybrain.bike.event;

import idv.funnybrain.bike.data.Station;

public class DataDownloadedEvent {
    private final Station[] data;

    public DataDownloadedEvent(Station[] data) {
        this.data = data;
    }

    public Station[] getData() {
        return data;
    }
}

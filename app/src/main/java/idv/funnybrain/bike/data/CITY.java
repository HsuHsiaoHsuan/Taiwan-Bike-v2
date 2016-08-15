package idv.funnybrain.bike.data;

/**
 * Created by freeman on 8/16/16.
 */

public enum CITY {
    TAIPEI(0),
    NEW_TAIPEI(1),
    TAOYUAN(2),
    HSINCHU(3),
    TAICHUNG(4),
    KAOHSIUNG(5);

    private int value;
    private String prefix = "http://61.216.94.105:5566";

    private CITY(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        switch (this) {
            case TAIPEI:
                return prefix + "/taipei";
            case NEW_TAIPEI:
                return prefix + "/new_taipei";
            case TAOYUAN:
                return prefix + "/taoyuan";
            case HSINCHU:
                return prefix + "/hsinchu";
            case TAICHUNG:
                return prefix + "/taichung";
            case KAOHSIUNG:
                return prefix + "/kaohsiung";
            default:
                return null;
        }
    }
}

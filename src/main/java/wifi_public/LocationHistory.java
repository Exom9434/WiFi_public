package wifi_public;

public class LocationHistory {
    private int id;
    private float lati;
    private float longi;
    private String searchedAt;

    public LocationHistory(int id, float lati, float longi, String searchedAt) {
        this.id = id;
        this.lati = lati;
        this.longi = longi;
        this.searchedAt = searchedAt;
    }

    public int getId() { return id; }
    public float getLat() { return lati; }
    public float getLon() { return longi; }
    public String getSearchedAt() { return searchedAt; }
}

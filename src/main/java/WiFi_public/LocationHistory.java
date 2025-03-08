package WiFi_public;

public class LocationHistory {
    private String id;
    private float xPos;
    private float yPos;
    private String searchedAt;

    public LocationHistory(String id, float xPos, float yPos, String searchedAt) {
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
        this.searchedAt = searchedAt;
    }

    public String getId() { return id; }
    public float getxPos() { return xPos; }
    public float getyPos() { return yPos; }
    public String getSearchedAt() { return searchedAt; }
}

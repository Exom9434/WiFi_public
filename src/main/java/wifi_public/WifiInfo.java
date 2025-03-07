package wifi_public;

public class WifiInfo {
    private String district;
    private String wifiName;
    private String roadAddress;
    private String detailAddress;
    private String installPosition;
    private String installType;
    private String installOrg;
    private String serviceType;
    private String channelType;
    private int installYear;
    private boolean isIn;
    private String accessEnv;
    private float lati;
    private float longi;
    private String workDate;
    private double distance;

    public WifiInfo(String district, String wifiName, String roadAddress, String detailAddress, String installPosition,
                    String installType, String installOrg, String serviceType, String channelType, int installYear,
                    boolean isIn, String accessEnv, float lati, float longi, String workDate, double distance) {
        this.district = district;
        this.wifiName = wifiName;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.installPosition = installPosition;
        this.installType = installType;
        this.installOrg = installOrg;
        this.serviceType = serviceType;
        this.channelType = channelType;
        this.installYear = installYear;
        this.isIn = isIn;
        this.accessEnv = accessEnv;
        this.lati = lati;
        this.longi = longi;
        this.workDate = workDate;
        this.distance = distance;
    }

    public double getDistance() { return distance; }
    public String getDistrict() { return district; }
    public String getWifiName() { return wifiName; }
    public String getRoadAddress() { return roadAddress; }
    public String getDetailAddress() { return detailAddress; }
    public String getInstallPosition() { return installPosition; }
    public String getInstallType() { return installType; }
    public String getInstallOrg() { return installOrg; }
    public String getServiceType() { return serviceType; }
    public String getChannelType() { return channelType; }
    public int getInstallYear() { return installYear; }
    public boolean isIn() { return isIn; }
    public String getAccessEnv() { return accessEnv; }
    public float getLati() { return lati; }
    public float getLongi() { return longi; }
    public String getWorkDate() { return workDate; }
}

package WiFi_public;

import java.sql.Timestamp;

public class WifiInfo {
    private String id;
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
    private String isIn;
    private String accessEnv;
    private float xPos;
    private float yPos;
    private Timestamp workDate;

    
    public WifiInfo(String id, String district, String wifiName, String roadAddress, String detailAddress,
                    String installPosition, String installType, String installOrg, String serviceType,
                    String channelType, int installYear, String isIn, String accessEnv, float xPos, float yPos,
                    Timestamp workDate) {
        this.id = id;
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
        this.xPos = xPos;
        this.yPos = yPos;
        this.workDate = workDate;
    }

    // 정보 가져올 수 있도록 구현 해야함(GET?)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getWifiName() { return wifiName; }
    public void setWifiName(String wifiName) { this.wifiName = wifiName; }

    public String getRoadAddress() { return roadAddress; }
    public void setRoadAddress(String roadAddress) { this.roadAddress = roadAddress; }

    public String getDetailAddress() { return detailAddress; }
    public void setDetailAddress(String detailAddress) { this.detailAddress = detailAddress; }

    public String getInstallPosition() { return installPosition; }
    public void setInstallPosition(String installPosition) { this.installPosition = installPosition; }

    public String getInstallType() { return installType; }
    public void setInstallType(String installType) { this.installType = installType; }

    public String getInstallOrg() { return installOrg; }
    public void setInstallOrg(String installOrg) { this.installOrg = installOrg; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getChannelType() { return channelType; }
    public void setChannelType(String channelType) { this.channelType = channelType; }

    public int getInstallYear() { return installYear; }
    public void setInstallYear(int installYear) { this.installYear = installYear; }

    public String getIsIn() { return isIn; }
    public void setIsIn(String isIn) { this.isIn = isIn; }

    public String getAccessEnv() { return accessEnv; }
    public void setAccessEnv(String accessEnv) { this.accessEnv = accessEnv; }

    public float getXPos() { return xPos; }
    public void setXPos(float xPos) { this.xPos = xPos; }

    public float getYPos() { return yPos; }
    public void setYPos(float yPos) { this.yPos = yPos; }

    public Timestamp getWorkDate() { return workDate; }
    public void setWorkDate(Timestamp workDate) { this.workDate = workDate; }
}

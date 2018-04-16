package app.pow.bundle;

import java.util.Map;

public class ManiFestInfo {
    private String app;
    private Map<String, Map<String, String>> mainfestInfo;

    public void setApp(String app) {
        this.app = app;
    }

    public String getApp() {
        return app;
    }

    public void setMainfestInfo(Map<String, Map<String, String>> mainfestInfo) {
        this.mainfestInfo = mainfestInfo;
    }

    public Map<String, Map<String, String>> getMainfestInfo() {
        return mainfestInfo;
    }

    @Override
    public String toString() {
        return "app: " + app + ",\n info: " + mainfestInfo;
    }
}

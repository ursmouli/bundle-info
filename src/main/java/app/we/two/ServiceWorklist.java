package app.we.two;

public class ServiceWorklist {

    private String serviceId;
    private String worklistId;
    private Integer serviceIndex = null;
    private Integer workListIndex = null;
    private Boolean serviceEnabled = null;
    private String permission;

    public ServiceWorklist() {}
    public ServiceWorklist(String serviceId, String worklistId, Integer serviceIndex, Integer workListIndex, Boolean serviceEnabled, String permission) {
        this.serviceId = serviceId;
        this.worklistId = worklistId;
        this.serviceIndex = serviceIndex;
        this.workListIndex = workListIndex;
        this.serviceEnabled = serviceEnabled;
        this.permission = permission;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getWorklistId() {
        return worklistId;
    }

    public void setWorklistId(String worklistId) {
        this.worklistId = worklistId;
    }

    public Integer getServiceIndex() {
        return serviceIndex;
    }

    public void setServiceIndex(Integer serviceIndex) {
        this.serviceIndex = serviceIndex;
    }

    public Integer getWorkListIndex() {
        return workListIndex;
    }

    public void setWorkListIndex(Integer workListIndex) {
        this.workListIndex = workListIndex;
    }

    public Boolean getServiceEnabled() {
        return serviceEnabled;
    }

    public void setServiceEnabled(Boolean serviceEnabled) {
        this.serviceEnabled = serviceEnabled;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}

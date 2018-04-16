package app.we.one;

public class WorkService {
    private String name;
    private Integer serviceIndex = null;
    private WorkList workList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkList getWorkList() {
        return workList;
    }

    public void setWorkList(WorkList workList) {
        this.workList = workList;
    }

    public Integer getServiceIndex() {
        return serviceIndex;
    }

    public void setServiceIndex(Integer serviceIndex) {
        this.serviceIndex = serviceIndex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name : " + getName());
        sb.append(", serviceIndex: " + getServiceIndex());
        sb.append(", workListIds: " + getWorkList().getIdList());
        sb.append(", workListIndexes: " + getWorkList().getIndexList());
        sb.append(", workListServiceEnabledList: " + getWorkList().getServiceEnabledList());
        sb.append(", workListPermissions: " + getWorkList().getPermissionList());
        return sb.toString();
    }
}

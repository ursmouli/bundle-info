package app.we.two;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorkService {
    private String name;
    private Integer serviceIndex = null;
    private Map<String, WorkList> workListMap = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServiceIndex() {
        return serviceIndex;
    }

    public void setServiceIndex(Integer serviceIndex) {
        this.serviceIndex = serviceIndex;
    }

    public Map<String, WorkList> getWorkListMap() {
        return workListMap;
    }

    public void setWorkListMap(Map<String, WorkList> workListMap) {
        this.workListMap = workListMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, WorkList> entry : getWorkListMap().entrySet()) {
            WorkList workList = entry.getValue();
            sb.append("name : " + getName());
            sb.append(", serviceIndex: " + getServiceIndex());
            sb.append(", workListId: " + workList.getId());
            sb.append(", workListIndexes: " + workList.getIndex());
            sb.append(", workListServiceEnabled: " + workList.getServiceEnabled());
            sb.append(", workListPermission: " + workList.getPermission());
            sb.append("\n");
        }
        return sb.toString();
    }
}

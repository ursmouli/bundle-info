package app.we.one;

import java.util.ArrayList;
import java.util.List;

public class WorkList {
    private List<String> idList = new ArrayList<>();
    private List<Integer> indexList = new ArrayList<>();
    private List<Boolean> serviceEnabledList = new ArrayList<>();
    private List<Boolean> permissionList = new ArrayList<>();

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public List<Integer> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<Integer> indexList) {
        this.indexList = indexList;
    }

    public List<Boolean> getServiceEnabledList() {
        return serviceEnabledList;
    }

    public void setServiceEnabledList(List<Boolean> serviceEnabledList) {
        this.serviceEnabledList = serviceEnabledList;
    }

    public List<Boolean> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Boolean> permissionList) {
        this.permissionList = permissionList;
    }
}

package app.we.one;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SpPermissionMain {
    private static final String PERM_ALLOW = "ALLOW";
    private static final String PERM_DISALLOW = "DISALLOW";

    public static void main(String[] args) {
        new SpPermissionMain().printNewSecurityProfile();
    }

    void printNewSecurityProfile() {
        List<ServiceWorklist> oldServiceWorklistLists = getOldServiceWorkLists();
        List<ServiceWorklist> newServiceWorklistLists = getNewServiceWorkLists();

        Map<String, WorkService> oldServiceMap = getServiceMap(oldServiceWorklistLists);
        Map<String, WorkService> newServiceMap = getServiceMap(newServiceWorklistLists);

        Set<String> newMapKeySet = newServiceMap.keySet();
        for (String newServiceKey : newMapKeySet) {
            if (oldServiceMap.containsKey(newServiceKey)) {
                newServiceMap.put(newServiceKey, oldServiceMap.get(newServiceKey));
            }
        }

        System.out.println("------------ old output ----------------------");
        for (String oldServiceKey : oldServiceMap.keySet()) {
            System.out.println(oldServiceMap.get(oldServiceKey));
        }
        System.out.println("------------ new output ----------------------");
        for (String newServiceKey : newMapKeySet) {
            System.out.println(newServiceMap.get(newServiceKey));
        }
        System.out.println("------------ old output map ----------------------");
        System.out.println(getPermission(oldServiceMap));
        System.out.println("------------ new output map ----------------------");
        System.out.println(getPermission(newServiceMap));
    }

    private Map<String, WorkService> getServiceMap(List<ServiceWorklist> serviceWorklists) {
        Map<String, WorkService> serviceMap = new HashMap<>();
        for (ServiceWorklist serviceWorklist : serviceWorklists) {
            WorkService workService = null;
            WorkList workList = null;

            if (serviceMap.containsKey(serviceWorklist.getServiceId())) {
                workService = serviceMap.get(serviceWorklist.getServiceId());
                workList = workService.getWorkList();
            } else {
                workService = new WorkService();
                workService.setServiceIndex(serviceWorklist.getServiceIndex());
                workService.setName(serviceWorklist.getServiceId());

                workList = new WorkList();
            }

            workList.getIdList().add(serviceWorklist.getWorklistId());
            workList.getIndexList().add(serviceWorklist.getWorkListIndex());
            workList.getServiceEnabledList().add(serviceWorklist.getServiceEnabled());
            boolean isEnabled = false;
            if (serviceWorklist.getPermission().equals(PERM_ALLOW)) {
                isEnabled = true;
            }
            workList.getPermissionList().add(isEnabled);

            workService.setWorkList(workList);

            serviceMap.put(serviceWorklist.getServiceId(), workService);
        }
        return serviceMap;
    }

    /**
     [[-1,0],[0,-1],[-1,1],[0,-1]]

     ServiceWorklist{serviceId=UpdateNew, worklistId=-1, serviceIndex=0, worklistIndex=0, Enabled=false, permission=DISALLOW}
     ServiceWorklist{serviceId=UpdateNew, worklistId=all, serviceIndex=0, worklistIndex=1, Enabled=true, permission=DISALLOW}

     ServiceWorklist{serviceId=Create, worklistId=-1, serviceIndex=1, worklistIndex=0, Enabled=true, permission=ALLOW}
     ServiceWorklist{serviceId=Create, worklistId=all, serviceIndex=1, worklistIndex=1, Enabled=false, permission=DISALLOW}

     ServiceWorklist{serviceId=Search, worklistId=-1, serviceIndex=2, worklistIndex=0, Enabled=false, permission=DISALLOW}
     ServiceWorklist{serviceId=Search, worklistId=all, serviceIndex=2, worklistIndex=1, Enabled=true, permission=ALLOW}

     ServiceWorklist{serviceId=Select, worklistId=-1, serviceIndex=3, worklistIndex=0, Enabled=true, permission=ALLOW}
     ServiceWorklist{serviceId=Select, worklistId=all, serviceIndex=3, worklistIndex=1, Enabled=false, permission=DISALLOW}
     */
    private List<ServiceWorklist> getNewServiceWorkLists() {
        List<ServiceWorklist> serviceWorklists = new ArrayList<>();

        serviceWorklists.add(new ServiceWorklist("UpdateNew", "-1", 0, 0, false, PERM_DISALLOW));
        serviceWorklists.add(new ServiceWorklist("UpdateNew", "all", 0, 1, true, PERM_DISALLOW));

        serviceWorklists.add(new ServiceWorklist("Create", "-1", 1, 0, true, PERM_DISALLOW));
        serviceWorklists.add(new ServiceWorklist("Create", "all", 1, 1, false, PERM_DISALLOW));

        serviceWorklists.add(new ServiceWorklist("Search", "-1", 2, 0, false, PERM_DISALLOW));
        serviceWorklists.add(new ServiceWorklist("Search", "all", 2, 1, true, PERM_DISALLOW));

        serviceWorklists.add(new ServiceWorklist("Select", "-1", 3, 0, true, PERM_DISALLOW));
        serviceWorklists.add(new ServiceWorklist("Select", "all", 3, 1, false, PERM_DISALLOW));

        return serviceWorklists;
    }

    private List<ServiceWorklist> getOldServiceWorkLists() {
        List<ServiceWorklist> serviceWorklists = new ArrayList<>();

        serviceWorklists.add(new ServiceWorklist("Create", "-1", 1, 0, true, PERM_ALLOW));
        serviceWorklists.add(new ServiceWorklist("Create", "all", 1, 1, false, PERM_DISALLOW));

        serviceWorklists.add(new ServiceWorklist("Update", "-1", 0, 0, false, PERM_DISALLOW));
        serviceWorklists.add(new ServiceWorklist("Update", "all", 0, 1, true, PERM_ALLOW));

        serviceWorklists.add(new ServiceWorklist("Search", "-1", 2, 0, false, PERM_DISALLOW));
        serviceWorklists.add(new ServiceWorklist("Search", "all", 2, 1, true, PERM_ALLOW));

        serviceWorklists.add(new ServiceWorklist("Select", "-1", 3, 0, true, PERM_ALLOW));
        serviceWorklists.add(new ServiceWorklist("Select", "all", 3, 1, false, PERM_DISALLOW));

        return serviceWorklists;
    }

    private String getPermission(Map<String, WorkService> serviceMap) {

        List<String> newPermList = new ArrayList<>();
        for (Map.Entry<String, WorkService> entry : serviceMap.entrySet()) {

            WorkList workList = entry.getValue().getWorkList();

            List<Boolean> permList = workList.getPermissionList();
            List<Boolean> serviceEnabledList = workList.getServiceEnabledList();

            List<String> iPermList = new ArrayList<>();
            for (int i = 0; i < permList.size(); i++) {
                String serviceStatus = serviceEnabledList.get(i) ? String.valueOf(1) : String.valueOf(-1);
                iPermList.add(permList.get(i) ? String.valueOf(0) : serviceStatus);
            }

            String[] iPermArr = new String[iPermList.size()];
            iPermArr = iPermList.toArray(iPermArr);

            newPermList.add(Arrays.toString(iPermArr));
        }

        String[] newStrPermArr = new String[newPermList.size()];
        newStrPermArr = newPermList.toArray(newStrPermArr);

        return Arrays.toString(newStrPermArr);
    }


}

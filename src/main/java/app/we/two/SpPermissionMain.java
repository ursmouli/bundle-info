package app.we.two;

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

            if (serviceMap.containsKey(serviceWorklist.getServiceId())) {
                workService = serviceMap.get(serviceWorklist.getServiceId());
            } else {
                workService = new WorkService();
                workService.setServiceIndex(serviceWorklist.getServiceIndex());
                workService.setName(serviceWorklist.getServiceId());
            }

            WorkList workList = null;
            if (workService.getWorkListMap().containsKey(serviceWorklist.getWorklistId())) {
                workList = workService.getWorkListMap().get(serviceWorklist.getWorklistId());
            } else {
                workList = new WorkList();
            }

            workList.setId(serviceWorklist.getWorklistId());
            workList.setIndex(serviceWorklist.getWorkListIndex());
            workList.setServiceEnabled(serviceWorklist.getServiceEnabled());
            boolean isPermEnabled = false;
            if (serviceWorklist.getPermission().equals(PERM_ALLOW)) {
                isPermEnabled = true;
            }
            workList.setPermission(isPermEnabled);

            workService.getWorkListMap().put(serviceWorklist.getWorklistId(), workList);

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

            Map<String, WorkList> workListMap = entry.getValue().getWorkListMap();

            List<String> permList = new ArrayList<>();

            for (Map.Entry<String, WorkList> workListEntry : workListMap.entrySet()) {
                String serviceStatus = workListEntry.getValue().getServiceEnabled() ? String.valueOf(1) : String.valueOf(-1);
                permList.add(workListEntry.getValue().getPermission() ? String.valueOf(0) : serviceStatus);
            }

            String[] sPermArr = new String[permList.size()];
            sPermArr = permList.toArray(sPermArr);

            newPermList.add(Arrays.toString(sPermArr));
        }

        String[] newStrPermArr = new String[newPermList.size()];
        newStrPermArr = newPermList.toArray(newStrPermArr);

        return Arrays.toString(newStrPermArr);
    }


}

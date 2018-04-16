package app.pow.bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompareFiles {
    public static void main(String[] args) {
        File mFile = new File("C:\\workspace\\latest\\powercurve\\originations\\bpa\\web\\admin\\dynamic-parameters-webapp\\target\\dynamic-parameters-webapp-1.10.12\\WEB-INF\\lib\\list.txt");
        File nFile = new File("C:\\workspace\\latest\\powercurve\\originations\\bpa\\web\\admin\\dynamic-parameters-webapp\\target\\dynamic-parameters-webapp-1.10.12\\WEB-INF\\lib\\list-naveen.txt");

        List<String> mList = getFilesInfo(mFile);
        List<String> nList = getFilesInfo(nFile);

        for (String tmp : nList) {
            if (!mList.contains(tmp)) {
                System.out.println(tmp);
            }
        }

    }

    public static List<String> getFilesInfo(File file) {

        List<String> iList = new ArrayList<String>();

        if (file.exists()) {
            String line = null;

            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    String finalStr = line.replaceAll("\\s+", "-split-");
                    String[] allInfo = finalStr.split("-split-");

                    if (allInfo.length == 5 && allInfo[4].endsWith(".jar")) {
                        iList.add(allInfo[4]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) br.close();
                    if (fr != null) fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
       return iList;
    }
}

package app.pow.bundle;

import app.pow.bundle.ManiFestInfo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class BundleInfoMain {
    public static void main(String[] args) throws Exception {

        List<ManiFestInfo> apps = new ArrayList<ManiFestInfo>();

        String path = "C:\\dev\\server-lib\\DAS\\";

        File root = new File(path);

        String[] directories = root.list(new FilenameFilter() {
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        ManiFestInfo bundle = null;
        for (String dir : directories) {
            bundle = new ManiFestInfo();
            bundle.setApp(dir);
            Map<String, Map<String, String>> manifestInfo = new HashMap<String, Map<String, String>>();
            File folder = new File(path + dir);
            String[] dirs = folder.list(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return new File(dir, name).isDirectory();
                }
            });

            for (String tmpDir : dirs) {
                if (tmpDir.equals("bundles")) {
                    for (File file : new File(path + dir + File.separator + tmpDir).listFiles()) {
                        Map<String, String> info = new HashMap<String, String>();
                        JarFile jar = null;
                        try {
                            jar = new JarFile(file.getAbsolutePath());
                            Manifest manifest = jar.getManifest();
                            Attributes mainAttributes = manifest.getMainAttributes();

                            for (Map.Entry entry : mainAttributes.entrySet()) {
                                info.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (jar != null) {
                                jar.close();
                            }
                        }

                        manifestInfo.put(file.getName(), info);

                        bundle.setMainfestInfo(manifestInfo);
                    }
                }
            }

            apps.add(bundle);
        }

        for (ManiFestInfo info : apps) {
            System.out.println("App : " + info);
            System.out.println("Bundle Info :");
            Map<String, Map<String, String>> mainfestInfo = info.getMainfestInfo();
            for (String key : mainfestInfo.keySet()) {
                System.out.println("\tJar: " + key);

                Map<String, String> data = mainfestInfo.get(key);
                for (String mi : data.keySet()) {
                    System.out.println("\t\t" + mi + " : " + data.get(mi));
                }
            }

        }
    }
}

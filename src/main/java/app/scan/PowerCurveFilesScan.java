package app.scan;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PowerCurveFilesScan {

    private boolean checkFolderForFileNames;

    private static final Logger LOG = LoggerFactory.getLogger(PowerCurveFilesScan.class);

    public static void main(String[] args) {
        PowerCurveFilesScan pfs = new PowerCurveFilesScan();

        String requiredExt = "java";

        List<File> allFilesInProject = pfs.getAllFilesInProject(PowerCurveFilesConstants.PROJECT_LOCATION.getValue(), requiredExt);

        List<File> filesContainingMatch = pfs.getFilesContainingMatch(allFilesInProject, PowerCurveFilesConstants.CLASS_INTERFACE_MATCHING.getValue());

        LOG.debug("matching content in string");
        List<File> allRegExpMatchingFiles = pfs.getAllRegExpMatchingFiles(filesContainingMatch);

        for (File file : allRegExpMatchingFiles) {
            LOG.info(file.getPath());
        }
        LOG.debug("end of matching content in string");
    }

    /**
     * To search in class 'class\s+\w+\s+implements(\s+).*(ILightSecurityTokenContext)'
     * To search in interface 'interface\s+\w+\s+extends(\s+).*(ILightSecurityTokenContext)'
     * @return
     */
    List<File> getAllRegExpMatchingFiles(Collection<File> files) {
        List<File> filesList = new ArrayList<File>();

        String strClassPattern = String.format(PowerCurveFilesConstants.CLASS_REGEXP_PATTERN.getValue(), PowerCurveFilesConstants.CLASS_INTERFACE_MATCHING.getValue());
        String strInterfacePattern = String.format(PowerCurveFilesConstants.INTERFACE_REGEXP_PATTERN.getValue(), PowerCurveFilesConstants.CLASS_INTERFACE_MATCHING.getValue());

        LOG.info(strClassPattern);
        LOG.info(strInterfacePattern);

        Pattern classPattern = Pattern.compile(strClassPattern);
        Pattern interfacePattern = Pattern.compile(strInterfacePattern);

        // now try to find at least one match
        for (File file : files) {
            String content = null;
            try {
                content = FileUtils.readFileToString(file);

                Matcher classMatcher = classPattern.matcher(content);
                Matcher interfaceMatcher = interfacePattern.matcher(content);
                if (classMatcher.find() || interfaceMatcher.find()) {
                    filesList.add(file);
                }
            } catch (IOException e) {
                LOG.error("error in reading file.", e);
            }
        }

        return filesList;
    }

    List<File> getAllFilesInProject(String path, String extension) {
        List<File> filesList = new ArrayList<File>();
        File sourceDir = new File(path);

        if (isCheckFolderForFileNames() && sourceDir.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(sourceDir, new String[]{extension}, true);
            LOG.debug("File size : " + files.size());
            filesList.addAll(files);

            StringBuilder filePaths = new StringBuilder();
            for (File file : files) {
                filePaths.append(file.getPath() + ";");
            }
            try {
                FileUtils.write(new File(PowerCurveFilesConstants.FILE_LIST_PATH.getValue()), filePaths.toString());
            } catch (IOException e) {
                LOG.error("failed writing to file " + PowerCurveFilesConstants.FILE_LIST_PATH.getValue(), e);
            }
        } else {
            try {
                final String fileListContent = FileUtils.readFileToString(new File(PowerCurveFilesConstants.FILE_LIST_PATH.getValue()));
                String[] splitStr = fileListContent.split(";");
                for (String str : splitStr) {
                    if (StringUtils.isNotEmpty(str)) {
                        filesList.add(new File(str));
                    }
                }
            } catch (IOException e) {
                LOG.error("error in reading file", e);
            }
        }

        return filesList;
    }

    List<File> getFilesContainingMatch(Collection<File> files, String matchStr) {
        List<File> matchingStrFiles = new ArrayList<File>();
        
        LOG.debug("printing all files");
        for (File file : files) {
            try {
                Pattern reqPattern = Pattern.compile(matchStr);
                Matcher matcher = reqPattern.matcher(FileUtils.readFileToString(file));
                if (matcher.find()) {
                    LOG.debug(matchStr + " file paths: " + file.getPath());
                    matchingStrFiles.add(file);
                }
            } catch (IOException e) {
                LOG.error("error in reading file.", e);
            }
        }
        LOG.debug("end of all printing");
        
        return matchingStrFiles;
    }

    public boolean isCheckFolderForFileNames() {
        return checkFolderForFileNames;
    }

    public void setCheckFolderForFileNames(boolean checkFolderForFileNames) {
        this.checkFolderForFileNames = checkFolderForFileNames;
    }
}

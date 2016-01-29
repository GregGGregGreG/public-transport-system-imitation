package ua.telesens.ostapenko.consoleapp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author root
 * @since 23.01.16
 */
public final class Bootstrap {

    private static final String APPLICATION_PROPERTIES = "application.properties";
    private String PROPERTY_NAME_ROOT_FOLDER;
    private String PROPERTY_NAME_LOG_FOLDER;
    private String PROPERTY_NAME_LOG_FILE;
    private String PROPERTY_NAME_REPORT_FOLDER;
    private String PROPERTY_NAME_PROPERTIES_IMITATION;

    public void installPatch() {
        readProperties();
        String workingDir = System.getProperty("user.dir");
        showMessage("Current operation system ", getOsName(), false);
        showMessage("Current working directory ", workingDir, false);

        Path path = Paths.get(workingDir);
        Path fileName = path.getFileName();

        Path parent = path.getParent();

        if (fileName.toString().equals(PROPERTY_NAME_ROOT_FOLDER)) {
            initLog(parent);
            initReport(parent);
            initProperties(parent);

        } else {
            showMessage("Not found root folder", PROPERTY_NAME_ROOT_FOLDER, false);
            System.exit(3);
        }
    }

    // Init imitation properties
    private void initProperties(Path parent) {
        Path imitation = Paths.get(parent + "/" + PROPERTY_NAME_PROPERTIES_IMITATION);
        if (!Files.exists(imitation)) {
            showMessage("File imitation properties not exists!", imitation.toUri().getPath(), false);
            System.exit(3);
        }

        try {
            File file = new File(imitation.toUri());
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            String PROPERTY_PATH_PROPERTIES_IMITATION = properties.getProperty("path.imitation.file");
            System.setProperty("path.properties.imitation.file", PROPERTY_PATH_PROPERTIES_IMITATION);

        } catch (IOException e) {
            showMessage("Error read imitation properties file", imitation.toUri().getPath(), false);
            System.exit(3);

        }
    }

    private void initReport(Path parent) {
        // report folder
        Path report = Paths.get(parent + "/" + PROPERTY_NAME_REPORT_FOLDER);
        if (!Files.exists(report)) {
            try {
                Files.createDirectories(report);
                showMessage("Directory from report created!", report.toUri().getPath(), false);
            } catch (IOException var2) {
                showMessage("Error report folder created!", var2.getMessage(), true);
                var2.printStackTrace();
                System.exit(2);
            }
        } else {
            showMessage("Directory report is exists!", report.toUri().getPath(), false);
        }
        System.setProperty("path.report.folder", report.toAbsolutePath().toString());
    }

    private void initLog(Path parent) {
        // logger folder
        Path log = Paths.get(parent + "/" + PROPERTY_NAME_LOG_FOLDER);
        if (!Files.exists(log)) {
            try {
                Files.createDirectories(log);
                showMessage("Directory from log created!", log.toUri().getPath(), false);
            } catch (IOException var1) {
                showMessage("Error log folder created!", var1.getMessage(), true);
                var1.printStackTrace();
                System.exit(2);
            }
        } else {
            showMessage("Directory log is exists!", log.toUri().getPath(), false);
        }
        System.setProperty("logfile.path", log.toAbsolutePath() + "/" + PROPERTY_NAME_LOG_FILE);
    }

    private String getOsName() {
        return System.getProperty("os.name") +
                "\t, version: " + System.getProperty("os.version") +
                "\t, arch: " + System.getProperty("os.arch");
    }

    private void readProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = getClass().getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES);
            // load a properties file
            prop.load(input);
            // get the property value
            PROPERTY_NAME_ROOT_FOLDER = prop.getProperty("name.root.folder");
            PROPERTY_NAME_LOG_FOLDER = prop.getProperty("name.log.folder");
            PROPERTY_NAME_REPORT_FOLDER = prop.getProperty("name.report.folder");
            PROPERTY_NAME_LOG_FILE = prop.getProperty("name.log.file");
            PROPERTY_NAME_PROPERTIES_IMITATION = prop.getProperty("name.properties.imitation.file");

        } catch (IOException var3) {
            showMessage("Error read properties", var3.getMessage(), true);
            var3.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException var4) {
                    showMessage("Error read properties", var4.getMessage(), true);
                    var4.printStackTrace();
                    System.exit(3);
                }
            }
        }
    }

    private static void showMessage(String title, String message, boolean error) {
        PrintStream stream = error ? System.err : System.out;
        stream.println("\n" + title + ": " + message);
    }

    public static void main(String[] args) {
        new Bootstrap().installPatch();
    }
}



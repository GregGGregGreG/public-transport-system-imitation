package ua.telesens.ostapenko;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author root
 * @since 23.01.16
 */
public class Bootstrap {

    private static final String APPLICATION_PROPERTIES = "application.properties";
    private String PROPERTY_NAME_ROOT_FOLDER;
    private String PROPERTY_NAME_LOG_FOLDER;
    private String PROPERTY_NAME_LOG_FILE;
    private String PROPERTY_NAME_REPORT_FOLDER;


    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();
    }

    public void run() {
        readProperties();
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);

        Path path = Paths.get(workingDir);
        Path fileName = path.getFileName();
        System.out.println(fileName);
        Path parent = path.getParent();
        System.out.println(parent);
        if (fileName.toString().equals(PROPERTY_NAME_ROOT_FOLDER)) {
            // logger folder
            File file = new File(parent + "/" + PROPERTY_NAME_LOG_FOLDER);
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory from log created!");
                } else {
                    System.out.println("Failed from log create directory!");
                }
            }

            System.setProperty("logfile.path", file.getAbsolutePath() + "/" + PROPERTY_NAME_LOG_FILE);

            // report folder
            file = new File(parent + "/" + PROPERTY_NAME_REPORT_FOLDER);
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory  from report created!");
                } else {
                    System.out.println("Failed from log create directory!");
                }
            }

            System.setProperty("path.report.folder", file.getAbsolutePath());
            System.out.println("dd");
        } else {
            throw new IllegalStateException("Not found root folder" + PROPERTY_NAME_ROOT_FOLDER);
        }
    }

    private void readProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

//            input = new FileInputStream(APPLICATION_PROPERTIES);

            input = getClass().getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES);
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            PROPERTY_NAME_ROOT_FOLDER = prop.getProperty("name.root.folder");
            PROPERTY_NAME_LOG_FOLDER = prop.getProperty("name.log.folder");
            PROPERTY_NAME_REPORT_FOLDER = prop.getProperty("name.report.folder");
            PROPERTY_NAME_LOG_FILE = prop.getProperty("name.log.file");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

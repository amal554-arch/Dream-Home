package dreamhome.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("⚠ Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String getDbUrl() {
        return "jdbc:mysql://" +
               get("db.host") + ":" +
               get("db.port") + "/" +
               get("db.name");
    }

    public static boolean isEmailEnabled() {
        return Boolean.parseBoolean(get("app.email.enabled"));
    }

    public static String getImagePath() {
        return get("image.directory");
    }
}

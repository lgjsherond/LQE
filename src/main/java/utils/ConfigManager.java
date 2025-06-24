package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static common.Constants.*;

public class ConfigManager {

    private static Properties properties;
    private static final String CONFIG_FILE = "src"+ File.separator+"main"+ File.separator+"resources"+ File.separator+"global.properties";

    static {
        loadProperties();
    }

    private static void loadProperties(){
        properties= new Properties();
        try {
            FileInputStream fis= new FileInputStream(CONFIG_FILE);
            properties.load(fis);
        } catch (IOException e){
            LoggerUtil.error("Could not load properties file: " + CONFIG_FILE + " Exception: " + e.getMessage());;
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        if (properties == null) {
            LoggerUtil.error("Properties have not been loaded.");
            return DEFAULT_BASE_URL;
        }
        String environment = getProperty("app.env");
        if(environment == null || environment.isEmpty()) {
            LoggerUtil.error("Environment property 'app.env' is not set.");
            return DEFAULT_BASE_URL;
        }
        switch (environment.toLowerCase()) {
            case "prod":
                return BASE_URL;
            case "qa":
                return QA_BASE_URL;
            case "pprod":
                return PPROD_BASE_URL;
            default:
                return DEFAULT_BASE_URL;
        }
    }
}

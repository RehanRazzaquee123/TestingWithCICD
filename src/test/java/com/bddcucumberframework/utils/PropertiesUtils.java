package com.bddcucumberframework.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

    public static String readProperty(String propertyName, String filePath) {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
            String propertyValue = properties.getProperty(propertyName);
            fileInputStream.close();
            return propertyValue;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
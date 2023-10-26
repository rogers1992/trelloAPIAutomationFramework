package com.trello.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesInfo {
    private static final Logger LOGGER = LogManager.getLogger(PropertiesInfo.class.getSimpleName());
    private static final String CONFIG_PROPERTIES = PathUtils.buildPath("gradle.properties");
    private static final String BASE_API = "apiUrl";
    private static final String PARTICULAR_PATH = PathUtils.buildPath(
            "trello-core/src/main/resources/propertiesEnv/");
    private static PropertiesInfo instance;
    private Properties properties;

    /**
     * Constructor method.
     */
    private PropertiesInfo() {
        loadProperties();
    }

    /**
     * This method is going to retrieve just one instance of the PropertiesInfo class.
     *
     * @return PropertiesInfo instance.
     */
    public static PropertiesInfo getInstance() {
        if (instance == null) {
            instance = new PropertiesInfo();
        }
        return instance;
    }

    /**
     * Method to load properties with general and particular information from properties files.
     */
    private void loadProperties() {
        try {
            FileInputStream generalProperties = new FileInputStream(CONFIG_PROPERTIES);
            properties = new Properties();
            properties.load(generalProperties);
            String particularFile = PARTICULAR_PATH.concat(getEnv()).concat(".properties");
            LOGGER.info("Particular properties to use: ".concat(particularFile));
            FileInputStream particularProperties = readProperties(particularFile);
            properties.load(particularProperties);
        } catch (IOException e) {
            throw new ClassCastException("A problem of type \n" + e.getMessage());
        }
    }

    /**
     * This method is going to read properties file.
     *
     * @param file path to read properties File.
     * @return new file Input Stream.
     */
    private FileInputStream readProperties(final String file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The properties file couldn't be found \n" + e.getMessage());
        }
    }

    /**
     * This method is going to retrieve the required property.
     *
     * @param propertyKey to be used.
     * @return value of the propertyKey.
     */
    private String getProperty(final String propertyKey) {
        String propertyValue = System.getProperty(propertyKey);
        if (propertyValue == null) {
            propertyValue = properties.getProperty(propertyKey);
        }
        return propertyValue;
    }

    /**
     * This method get base api.
     *
     * @return base api.
     */
    public String getBaseApi() {
        return getProperty(BASE_API);
    }

    /**
     * This metho gets the api key.
     *
     * @return api key.
     */
    public String getApiKey() {
        return getProperty("apiKey");
    }

    /**
     * This method gets the api token.
     *
     * @return api token.
     */
    public String getApiToken() {
        return getProperty("apiToken");
    }

    /**
     * This method gets the api version.
     *
     * @return api version.
     */
    public String getApiVersion() {
        return getProperty("version");
    }


    /**
     * This method gets environment.
     *
     * @return environment.
     */
    public String getEnv() {
        return getProperty("env");
    }

    /**
     * Method to set a property in properties info.
     *
     * @param property key.
     * @param value    to set.
     */
    public void setProperty(final String property, final String value) {
        this.properties.setProperty(property, value);
    }
}

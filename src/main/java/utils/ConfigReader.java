package utils;

import org.aeonbits.owner.ConfigFactory;

public class ConfigReader {
    private static ConfigProperties configProperties = ConfigFactory.create(ConfigProperties.class);

    public static String getBrowser() {
        return configProperties.browser();
    }

    public static boolean isHeadless() {
        return configProperties.headless();
    }

    public static String getBaseUrl() {
        return configProperties.baseUrl();
    }

    public static String getSmtpHost() {
        return configProperties.smtpHost();
    }

    public static int getSmtpPort() {
        return configProperties.smtpPort();
    }

    public static String getSmtpUser() {
        return configProperties.smtpUser();
    }

    public static String getSmtpPassword() {
        return configProperties.smtpPassword();
    }

    public static String getRecipient() {
        return configProperties.recipient();
    }
}
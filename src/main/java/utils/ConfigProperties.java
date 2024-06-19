package utils;

import org.aeonbits.owner.Config;

@Config.Sources({
        "file:resources/config.properties",
        "system:properties"
})
public interface ConfigProperties extends Config {
    @Key("browser")
    String browser();

    @Key("headless")
    boolean headless();

    @Key("baseUrl")
    String baseUrl();

    @Key("smtp.host")
    String smtpHost();

    @Key("smtp.port")
    int smtpPort();

    @Key("smtp.user")
    String smtpUser();

    @Key("smtp.password")
    String smtpPassword();

    @Key("recipient")
    String recipient();

    @Key("implicitlyWait")
    long implicitlyWait();

    @Key("setScriptTimeout")
    long setScriptTimeout();

    @Key("pageLoadTimeout")
    long pageLoadTimeout();
}
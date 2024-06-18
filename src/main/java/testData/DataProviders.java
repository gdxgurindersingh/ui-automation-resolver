package testData;
import org.testng.annotations.DataProvider;

public class DataProviders {
    @DataProvider(name = "loginCredentials")
    public static Object[][] loginCredentials() {
        return new Object[][] {
                {"user@resolver.com", "password"},
                {"admin@resolver.com", "userpassword"}
        };
    }
}
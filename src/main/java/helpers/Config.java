package helpers;

public class Config {

    public static String getUser() {
        return System.getProperty("user", PropertyReader.getProperty("user"));
    }

    public static String getPassword() {
        return System.getProperty("password", PropertyReader.getProperty("password"));
    }

    public static String getToken() {
        return System.getProperty("token", PropertyReader.getProperty("token"));
    }

    public static String getBrowser() {
        return System.getProperty("browser", PropertyReader.getProperty("browser"));
    }

    public static String getBaseUrl() {
        return System.getProperty("baseUrl", PropertyReader.getProperty("baseUrl"));
    }


}

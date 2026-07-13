package utils;

public class TestDataGenerator {

    public static String generateProjectName() {
        return "Test-" + System.currentTimeMillis();
    }

    public static String generateProjectCode() {
        return "T" + (System.currentTimeMillis() % 100000);
    }
}

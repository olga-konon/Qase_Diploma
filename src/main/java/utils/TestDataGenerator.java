package utils;

public class TestDataGenerator {

    public static String generateProjectName() {
        return "Name-" + System.currentTimeMillis();
    }

    public static String generateProjectCode() {
        return "Code" + (System.currentTimeMillis() % 100000);
    }
    public static String generateCaseTitle() {
        return "Case" + (System.currentTimeMillis() % 100000);
    }


}

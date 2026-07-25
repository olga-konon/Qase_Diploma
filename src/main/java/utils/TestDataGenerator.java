package utils;

import java.util.concurrent.atomic.AtomicLong;

public class TestDataGenerator {

    private static final AtomicLong COUNTER = new AtomicLong();

    public static String generateProjectName() {
        return "Name-" + System.currentTimeMillis() + COUNTER.incrementAndGet();
    }

    public static String generateProjectCode() {
        return "Code" + (System.currentTimeMillis() % 100000) + COUNTER.incrementAndGet();
    }

    public static String generateCaseTitle() {
        return "Case" + (System.currentTimeMillis() % 100000) + COUNTER.incrementAndGet();
    }

}

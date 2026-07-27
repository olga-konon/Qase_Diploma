package utils;

import java.util.concurrent.atomic.AtomicLong;

public class TestDataGenerator {

    private static final AtomicLong COUNTER = new AtomicLong();

    public static String generateProjectName() {
        return "NAME" + (System.currentTimeMillis() % 100000) + (COUNTER.incrementAndGet() % 10000);
    }

    public static String generateProjectCode() {
        return "C" + (System.currentTimeMillis() % 100000) + (COUNTER.incrementAndGet() % 10000);
    }

    public static String generateCaseTitle() {
        return "CASE" + (System.currentTimeMillis() % 100000) + (COUNTER.incrementAndGet() % 10000);
    }
}

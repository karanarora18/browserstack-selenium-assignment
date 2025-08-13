package io.github.karanarora18.frameworkconfig;

import java.util.Map;
import java.util.HashMap;

public class TestConfig {

    // ThreadLocal store for parallel execution
    private static final ThreadLocal<Map<String, String>> configStore =
            ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, String value) {
        configStore.get().put(key, value);
    }

    public static String get(String key) {
        return configStore.get().get(key);
    }

    public static String get(String key, String defaultValue) {
        String value = configStore.get().get(key);
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }

    public static void clear() {
        configStore.remove();
    }
}

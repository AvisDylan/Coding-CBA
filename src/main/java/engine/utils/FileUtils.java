package engine.utils;

import java.io.IOException;

public final class FileUtils {

    public static String readFileToString(String path) throws IOException {
        return new String(Thread.currentThread().getContextClassLoader().getResourceAsStream(path).readAllBytes());
    }
}

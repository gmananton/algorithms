import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;

import java.nio.charset.StandardCharsets;

public class FileHelper {

    public static String loadResource(String resourceName) {
        try {
            return Resources.toString(Resources.getResource(resourceName), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T resourceToType(String resourceName, Class<T> type) {
        try {
            return new ObjectMapper().readValue(loadResource(resourceName), type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

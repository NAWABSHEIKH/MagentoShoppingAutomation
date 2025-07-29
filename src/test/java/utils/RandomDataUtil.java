// ✅ Step 4: RandomDataUtil.java
package utils;

import java.util.UUID;

public class RandomDataUtil {
    public static String generateRandomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@mailinator.com";
    }
}
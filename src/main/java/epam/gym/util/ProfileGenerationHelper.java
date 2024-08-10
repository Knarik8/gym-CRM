package epam.gym.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProfileGenerationHelper {


    public static String generateUsername(String firstName, String lastName, Set<String> existingUsernames) {

        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int counter = 1;
        while (existingUsernames.contains(username)) {
            username = baseUsername + counter;
            counter++;
        }
        return username;
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}

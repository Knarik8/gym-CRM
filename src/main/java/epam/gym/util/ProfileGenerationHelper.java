package epam.gym.util;

import epam.gym.storage.InMemoryStorage;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Set;

public class ProfileGenerationHelper {

    private final InMemoryStorage storage;

    public ProfileGenerationHelper(InMemoryStorage storage) {
        this.storage = storage;
    }
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

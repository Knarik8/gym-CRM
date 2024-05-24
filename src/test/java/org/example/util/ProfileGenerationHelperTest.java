package org.example.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ProfileGenerationHelperTest {

    private Set<String> existingUsernames;

    @BeforeEach()
    public void setUp(){
        existingUsernames = new HashSet<>();
    }

    @Test
    @DisplayName("When username not exist should generate it by concatenation first name and last name " +
            "by using dot as a separator")
    void generateUsernameWhenNotExist() {

        String generatedUsername = ProfileGenerationHelper.generateUsername("Anna", "Petrova",
                existingUsernames);
        assertEquals("Anna.Petrova", generatedUsername);
    }

    @Test
    @DisplayName("when username already exist should add number")
    void generateUsernameWhenExist() {
        existingUsernames.add("Anna.Smirnova");

        String generatedUsername = ProfileGenerationHelper.generateUsername("Anna", "Smirnova",
                existingUsernames);
        assertEquals("Anna.Smirnova1", generatedUsername);

        existingUsernames.add(generatedUsername);
        generatedUsername = ProfileGenerationHelper.generateUsername("Anna", "Smirnova", existingUsernames);
        assertEquals("Anna.Smirnova2", generatedUsername);
    }

    @Test
    @DisplayName("Should be random 10 chars length string")
    void generatePassword() {
        String generatedPassword = ProfileGenerationHelper.generatePassword();
        assertNotNull(generatedPassword);
        assertEquals(10, generatedPassword.length());
        assertTrue(generatedPassword.matches("[A-Za-z0-9]+"));
    }
}

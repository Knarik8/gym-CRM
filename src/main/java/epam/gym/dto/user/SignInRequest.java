package epam.gym.dto.user;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}

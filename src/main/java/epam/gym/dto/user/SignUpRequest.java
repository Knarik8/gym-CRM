package epam.gym.dto.user;

import epam.gym.entity.Address;
import lombok.Data;

import java.time.LocalDate;


@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String userType; //trainee or trainer
    private LocalDate dateOfBirth;
    private Address address;
}

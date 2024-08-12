package epam.gym.dto.trainee;

import epam.gym.entity.Address;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TraineeUpdateDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Address address;
    private boolean isActive;
}

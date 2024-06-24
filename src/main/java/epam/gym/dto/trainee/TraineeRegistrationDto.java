package epam.gym.dto.trainee;

import epam.gym.entity.Address;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TraineeRegistrationDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Address address;

}

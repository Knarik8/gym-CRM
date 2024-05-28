package epam.gym.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Trainee extends User {

    private LocalDate dateOfBirth;
    private Address address;

    public Trainee(Long id, String firstName, String lastName, String username, String password, boolean isActive,
                   LocalDate dateOfBirth, Address address) {
        super(id, firstName, lastName, username, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

}

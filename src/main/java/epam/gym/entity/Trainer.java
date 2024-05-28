package epam.gym.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Trainer extends User {

    private TrainingType specialization;


    public Trainer(Long id, String firstName, String lastName, String username, String password, boolean isActive,
                   TrainingType specialization){
        super(id, firstName, lastName, username, password, isActive);
        this.specialization = specialization;
    }

}

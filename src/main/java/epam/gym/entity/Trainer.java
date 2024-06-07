package epam.gym.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "trainer")
@SuperBuilder
public class Trainer extends User {

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingTypeEntity specialization;

    @ManyToMany(mappedBy = "trainers")
    @ToString.Exclude
    private Set<Trainee> trainees = new HashSet<>();

    @OneToMany(mappedBy = "trainer")
    private Set<Training> trainings = new HashSet<>();

}

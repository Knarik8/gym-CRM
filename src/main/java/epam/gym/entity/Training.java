package epam.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Duration;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "training")
@Builder
public class Training {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String trainingName;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingTypeEntity trainingType;

    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> trainingDays;

    private Duration trainingDuration;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

}

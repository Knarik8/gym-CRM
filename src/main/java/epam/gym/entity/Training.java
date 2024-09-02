package epam.gym.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "training")
@Builder
@EqualsAndHashCode(callSuper = false)
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

    private Long trainingDuration;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    @ToString.Exclude
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @ToString.Exclude
    @JsonIgnore
    private Trainer trainer;

    private LocalDateTime trainingDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return id != null && id.equals(training.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

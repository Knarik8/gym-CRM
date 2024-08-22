package epam.gym.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "trainer")
@SuperBuilder
public class Trainer extends User {

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingTypeEntity specialization;

    @ManyToMany(mappedBy = "trainers", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Trainee> trainees = new HashSet<>();

    @OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private Set<Training> trainings = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return getId() != null && getId().equals(trainer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}

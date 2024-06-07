package epam.gym.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "trainee")
@SuperBuilder
public class Trainee extends User {

    private LocalDate dateOfBirth;


    @OneToOne(mappedBy = "trainee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "training",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    @ToString.Exclude
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = "trainee", fetch = FetchType.EAGER)
    private Set<Training> trainings = new HashSet<>();

}

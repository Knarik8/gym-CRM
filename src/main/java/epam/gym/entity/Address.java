package epam.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String street;

    private int buildingNumber;

    private int apartmentNumber;

    @OneToOne
    @JoinColumn(name = "trainee_id")
    @ToString.Exclude
    private Trainee trainee;


}

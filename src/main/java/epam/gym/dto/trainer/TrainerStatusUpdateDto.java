package epam.gym.dto.trainer;

import lombok.Data;

@Data
public class TrainerStatusUpdateDto {

    private String username;
    private boolean isActive;
}

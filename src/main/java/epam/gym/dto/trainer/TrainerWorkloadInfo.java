package epam.gym.dto.trainer;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class TrainerWorkloadInfo {


    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;

    private Map<Integer, Map<Integer, Long>> yearlyWorkload = new HashMap<>();
}

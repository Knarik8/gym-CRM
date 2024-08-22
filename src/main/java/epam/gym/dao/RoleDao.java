package epam.gym.dao;

import epam.gym.entity.Role;
import epam.gym.entity.RoleEntity;

import java.util.Optional;

public interface RoleDao {
    Optional<RoleEntity> findByName(Role name);

}

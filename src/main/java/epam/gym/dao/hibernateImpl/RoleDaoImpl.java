package epam.gym.dao.hibernateImpl;

import epam.gym.dao.RoleDao;
import epam.gym.entity.Role;
import epam.gym.entity.RoleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RoleEntity> findByName(Role name) {
        TypedQuery<RoleEntity> query = entityManager.createQuery(
                "SELECT r FROM RoleEntity r WHERE r.name = :name", RoleEntity.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }
}

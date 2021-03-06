package webAppProject.persistence.repository;
import org.springframework.data.repository.CrudRepository;
import webAppProject.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    public Role findById(int id);
}
package webAppProject.persistence.repository;
import org.springframework.data.repository.CrudRepository;
import webAppProject.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByLogin(String login);
}
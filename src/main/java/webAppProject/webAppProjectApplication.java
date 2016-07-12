package webAppProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import webAppProject.domain.Role;
import webAppProject.domain.User;
import webAppProject.persistence.repository.UserRepository;

@SpringBootApplication
public class webAppProjectApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(webAppProjectApplication.class, args);
    }

    @Autowired
    private UserRepository userRepo;

    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 2;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();

        User user = new User();
        user.setLogin("alice");
        user.setPassword(pe.encode("password"));
        Role role = new Role();
        role.setId(ROLE_USER);
        role.setRole("USER");
        user.setRole(role);
        userRepo.save(user);

        user = new User();
        user.setLogin("bob");
        user.setPassword(pe.encode("admin"));
        role = new Role();
        role.setId(ROLE_ADMIN);
        role.setRole("ADMIN");
        user.setRole(role);
        userRepo.save(user);

    }

}

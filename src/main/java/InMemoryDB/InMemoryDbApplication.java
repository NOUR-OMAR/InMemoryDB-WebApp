package InMemoryDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = {"InMemoryDB"})
@EnableWebSecurity
public class InMemoryDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(InMemoryDbApplication.class, args);
    }

}

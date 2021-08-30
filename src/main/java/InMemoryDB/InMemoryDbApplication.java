package InMemoryDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"InMemoryDB"})
public class InMemoryDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(InMemoryDbApplication.class, args);
    }

}

package InMemoryDB.client.model;

import InMemoryDB.common.menu.Menu;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

@Data
public class User {

    private String username;
    private String password;
    private Menu menu;


    public User() {
    }


    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    @NotNull
    public static User registerUser(Scanner scanner) {
        String userName;
        String password;
        System.out.println("Enter the username: ");
        userName = scanner.nextLine();
        System.out.println("Enter the password: ");
        password = scanner.nextLine();
        return new Employee(userName, password);
    }

    public void display(Menu menu) {
        menu.displayMenu();
    }


}

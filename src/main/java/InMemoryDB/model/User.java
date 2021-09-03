package InMemoryDB.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class User {


    private int id;
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    private String role;


    public User() {
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String userName, String password, String role) {
        this.username = userName;
        this.password = password;
        this.role = role;
    }

    public User(int id, String userName, String password, String role) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.role = role;
    }


  /*  @NotNull
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
*/

}

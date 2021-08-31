package InMemoryDB.model;

import lombok.Data;

@Data
public class User {

    private int id;
    private String username;
    private String password;
    private String role;

    private boolean enabled;

    public User() {
    }


    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }
    public User(String userName, String password,String role) {
        this.username = userName;
        this.password = password;
        this.role=role;
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

package InMemoryDB.model;

import lombok.Data;

@Data
public class User {


    private int id;
    private String username;
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

}

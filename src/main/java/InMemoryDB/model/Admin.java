package InMemoryDB.model;

import lombok.Data;


@Data
public class Admin extends User {

    public Admin(String userName, String password,String role) {
        super(userName, password,"ADMIN");
    }


}

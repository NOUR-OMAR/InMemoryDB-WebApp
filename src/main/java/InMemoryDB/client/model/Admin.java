package InMemoryDB.client.model;

import lombok.Data;

@Data
public class Admin extends User {

    public Admin(String userName, String password) {
        super(userName, password);
    }


}

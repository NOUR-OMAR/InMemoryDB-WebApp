package InMemoryDB.client.model;

import lombok.Data;

import java.util.HashMap;


@Data
public class Department {

    int id;
    String name;
    String location;
    public static HashMap<String, Department> departments = new HashMap<>();

    public Department(String id, String name, String location) {
        setId(id);
        this.name = name;
        this.location = location;
    }

    public Department() {

    }

    public Department(String id) {
        setId(id);
    }

    public void setId(String id) {
        setId(Integer.parseInt(id));
    }


    public void setId(int id) {

        this.id = id;
    }


}

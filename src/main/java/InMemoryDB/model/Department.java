package InMemoryDB.model;

import lombok.Data;

import java.util.HashMap;


@Data
public class Department {

    int id;
    String name;
    String location;
    public static HashMap<Integer, Department> departments = new HashMap<>();

    public Department(int id, String name, String location) {
       this.id=id;
        this.name = name;
        this.location = location;
    }

    public Department() {

    }

    public Department(int id) {
        this.id=id;
    }

}

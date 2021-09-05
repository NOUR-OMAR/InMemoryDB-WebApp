package InMemoryDB.model;


import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class Employee extends User {
    private int id;
    private String name;
    private int salary;
    private Department department;
    public Employee(int id, String name, int salary) {
        this.id=id;
        this.salary=salary;
        this.name = name;
    }

    public Employee() {
    }

    public Employee(int id, String name, int salary, int departmentId) {
       this.id=id;
       this.salary=salary;
        this.name = name;

       department=new Department();
       department.setId(departmentId);
       department.setName(Department.departments.get(departmentId).getName());
       department.setLocation(Department.departments.get(departmentId).getLocation());
    }

    public Employee(int id,String username, String password,String role) {
        super(id,username, password,role);
    }






}


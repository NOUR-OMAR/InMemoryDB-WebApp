package InMemoryDB.client.model;


import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class Employee extends User {
    private int id;
    private String name;
    private int salary;
    private Department department;

    public Employee(String id, String name, String salary) {
        setId(id);
        setSalary(salary);
        this.name = name;
    }

    public Employee() {
    }

    public Employee(String id, String name, String salary, String departmentId) {
        setId(id);
        setSalary(salary);
        this.name = name;
        getDepartment().setId(departmentId);

    }

    public Employee(String userName, String password) {
        super(userName, password);
    }


    public void setId(String id) {
        setId(Integer.parseInt(id));
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setSalary(String salary) {
        setSalary(Integer.parseInt(salary));
    }

    public void setSalary(int salary) {

        this.salary = salary;
    }


}


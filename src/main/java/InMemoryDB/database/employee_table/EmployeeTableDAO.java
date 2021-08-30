package InMemoryDB.database.employee_table;

import InMemoryDB.client.model.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//DAO (Data Access Object) class
//that provides CRUD (Create, Read, Update, Delete) operations as well as other operations for the table Employee in the server.database.
public interface EmployeeTableDAO {
    void createEmployee(Employee employee) throws IOException;

    void updateEmployee(Employee employee);

    void deleteEmployee(int id);
//TODO make filter table
    Employee readEmployee(int id);

    Map<Integer, Employee> filterByName(String name);

    Map<Integer, Employee> filterBySalaryGT(int salary);

    Map<Integer, Employee> filterBySalaryLT(int salary);

    Map<Integer, Employee> filterBySalaryEQ(int salary);

    void close() throws IOException;

    List<Employee> selectAll();

}

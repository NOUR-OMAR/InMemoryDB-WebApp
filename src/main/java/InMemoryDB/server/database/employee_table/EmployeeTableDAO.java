package InMemoryDB.server.database.employee_table;

import InMemoryDB.client.model.Employee;

import java.io.IOException;
import java.util.Map;

//DAO (Data Access Object) class
//that provides CRUD (Create, Read, Update, Delete) operations as well as other operations for the table Employee in the server.database.
public interface EmployeeTableDAO {
    Employee createEmployee(String id, String name, String salary) throws IOException;

    Employee updateEmployee(String id, String name, String salary);

    void deleteEmployee(String id);

    Employee readEmployee(String id);

    Map<Integer, Employee> filterByName(String name);

    Map<Integer, Employee> filterBySalaryGT(int salary);

    Map<Integer, Employee> filterBySalaryLT(int salary);

    Map<Integer, Employee> filterBySalaryEQ(int salary);

    void close() throws IOException;

    String selectAll();

}
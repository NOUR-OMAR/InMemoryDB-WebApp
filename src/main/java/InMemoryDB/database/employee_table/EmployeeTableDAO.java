package InMemoryDB.database.employee_table;

import InMemoryDB.model.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//DAO (Data Access Object) class
//that provides CRUD (Create, Read,
//
//
//
// roUpdate, Delete) operations as well as other operations for the table Employee in the server.database.
public interface EmployeeTableDAO {
    void createEmployee(Employee employee) throws IOException;

    void updateEmployee(Employee employee) throws IOException;

    void deleteEmployee(int id) throws IOException;

    Employee readEmployee(int id) throws IOException;

    Map<Integer, Employee> filterByName(String name) throws IOException;

    Map<Integer, Employee> filterBySalaryGT(int salary) throws IOException;

    Map<Integer, Employee> filterBySalaryLT(int salary) throws IOException;

    Map<Integer, Employee> filterBySalaryEQ(int salary) throws IOException;

    void close() throws IOException;

    List<Employee> selectAll() throws IOException;

}

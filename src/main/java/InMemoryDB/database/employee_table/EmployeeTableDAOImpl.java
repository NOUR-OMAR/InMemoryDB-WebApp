package InMemoryDB.database.employee_table;

import InMemoryDB.database.Database;
import InMemoryDB.model.Employee;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static InMemoryDB.utils.Constant.Display.display;


@Data
@Component
public class EmployeeTableDAOImpl implements EmployeeTableDAO {

    @Autowired
    private final Database database;

    @Override
    public void createEmployee(Employee employee) throws IOException {
        if (database.getEmployee(employee.getId()) == null) {

            //  Employee newEmployee = new Employee(employee.getId(), employee.getName(), employee.getSalary(),employee.getDepartment().getId());
            database.putInEmployeesTable(employee);
            // return newEmployee;
        } else {
            display("Can't create, employee with id " + employee.getId() + " already existed.");
            //   return null;
        }
    }

    @Override
    public Employee readEmployee(int id) {
        return getDatabase().getEmployee(id);
    }

    @Override
    public void updateEmployee(Employee employee) throws IOException {
        if (database.getEmployee(employee.getId()) != null) {
            //Employee newEmployee = new Employee(id, name, salary,
            // departmentId);
            database.putInEmployeesTable(employee);
            // return newEmployee;
        } else {
            display("Can't update, employee with id " + employee.getId() + " doesn't exist.");
            // return null;
        }
    }

    @Override
    public void deleteEmployee(int id) throws IOException {
        synchronized (database) {
            if (Database.getTableLRUCache().snapshot().containsKey(id)) {
                database.removeFromTableCache(id);
            } else if (database.getEmployeesTable().containsKey(id)) {
                database.removeFromEmployeeTable(id);
            } else {
                display("Can't delete,employee with id " + id + " doesn't exist");
            }

        }
    }


    @Override
    public Map<Integer, Employee> filterByName(String name) {

        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : database.getEmployeesTable().entrySet()) {
            if (employee.getValue().getName().toLowerCase().startsWith(name.toLowerCase())) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;

    }

    @Override
    public Map<Integer, Employee> filterBySalaryEQ(int salary) {

        Map<Integer, Employee> employeeHashMap = new HashMap<>();

        for (Map.Entry<Integer, Employee> employee : database.getEmployeesTable().entrySet()) {
            if (employee.getValue().getSalary() == salary) {
                employeeHashMap.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashMap;


    }

    @Override
    public Map<Integer, Employee> filterBySalaryLT(int salary) {


        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : database.getEmployeesTable().entrySet()) {
            if (employee.getValue().getSalary() < salary) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;
    }


    @Override
    public Map<Integer, Employee> filterBySalaryGT(int salary) {

        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : database.getEmployeesTable().entrySet()) {
            if (employee.getValue().getSalary() > salary) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;
    }

    @Override
    public void close() throws IOException {
        database.close();
    }

    @Override
    public List<Employee> selectAll() {
        return new ArrayList<>(database.getEmployeesTable().values());
    }

}

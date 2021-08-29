package InMemoryDB.server.database.employee_table;

import InMemoryDB.client.model.Employee;
import InMemoryDB.server.database.Database;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static InMemoryDB.server.database.Database.getInitialisedDatabase;
import static InMemoryDB.utils.Constant.Display.display;


@Data
@Service
public class EmployeeTableDAOImpl implements EmployeeTableDAO {


    private final Database database;


    public EmployeeTableDAOImpl(Database database) {
        this.database = database;
    }

    private EmployeeTableDAOImpl() throws IOException {
        this.database = getInitialisedDatabase();
    }

    public static EmployeeTableDAO createEmployeeDAOTableImpl() throws IOException {
        return new EmployeeTableDAOImpl();
    }


    @Override
    public Employee createEmployee(String id, String name, String salary) {
        if (getDatabase().getEmployee(Integer.parseInt(id)) == null) {
            Employee newEmployee = new Employee(id, name, salary);
            getDatabase().putInEmployeesTable(newEmployee);
            return newEmployee;
        } else {
            display("Can't create, employee with id " + id + " already existed.");
            return null;
        }
    }

    @Override
    public Employee readEmployee(String id) {
        return getDatabase().getEmployee(Integer.parseInt(id));
    }

    @Override
    public Employee updateEmployee(String id, String name, String salary) {
        if (getDatabase().getEmployee(Integer.parseInt(id)) != null) {
            Employee newEmployee = new Employee(id, name, salary);
            getDatabase().putInEmployeesTable(newEmployee);
            return newEmployee;
        } else {
            display("Can't update, employee with id " + id + " doesn't exist.");
            return null;
        }
    }

    @Override
    public void deleteEmployee(String id) {
        synchronized (getDatabase()) {
            if (Database.getTableLRUCache().snapshot().containsKey(Integer.parseInt(id))) {
                getDatabase().removeFromTableCache(Integer.parseInt(id));
            } else if (Database.getAllEmployees().containsKey(Integer.parseInt(id))) {
                getDatabase().removeFromEmployeeTable(Integer.parseInt(id));
            } else {
                display("Can't delete,employee with id " + id + " doesn't exist");
            }

        }
    }


    @Override
    public Map<Integer, Employee> filterByName(String name) {

        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : Database.getAllEmployees().entrySet()) {
            if (employee.getValue().getName().toLowerCase().startsWith(name.toLowerCase())) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;

    }

    @Override
    public Map<Integer, Employee> filterBySalaryEQ(int salary) {

        Map<Integer, Employee> employeeHashMap = new HashMap<>();

        for (Map.Entry<Integer, Employee> employee : Database.getAllEmployees().entrySet()) {
            if (employee.getValue().getSalary() == salary) {
                employeeHashMap.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashMap;


    }

    @Override
    public Map<Integer, Employee> filterBySalaryLT(int salary) {


        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : Database.getAllEmployees().entrySet()) {
            if (employee.getValue().getSalary() < salary) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;
    }


    @Override
    public Map<Integer, Employee> filterBySalaryGT(int salary) {

        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : Database.getAllEmployees().entrySet()) {
            if (employee.getValue().getSalary() > salary) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;
    }

    @Override
    public void close() throws IOException {
        getInitialisedDatabase().close();
    }

    @Override
    public List<Employee> selectAll() {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : getDatabase().getEmployeesTable().values())
            employees.add(employee);
        return employees;
    }

}

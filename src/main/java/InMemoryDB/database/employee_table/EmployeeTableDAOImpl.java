package InMemoryDB.database.employee_table;

import InMemoryDB.database.Database;
import InMemoryDB.model.Employee;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static InMemoryDB.database.Database.getDatabase;
import static InMemoryDB.utils.Display.display;


@Data
@Component
public class EmployeeTableDAOImpl implements EmployeeTableDAO {

    //  @Autowired
    //private final Database database;

    @Override
    public void createEmployee(Employee employee) throws IOException {
        if (getDatabase().getEmployee(employee.getId()) == null) {

            getDatabase().putInEmployeesTable(employee);
        } else {
            display("Can't create, employee with id " + employee.getId() + " already existed.");
        }
    }

    @Override
    public Employee readEmployee(int id) throws IOException {
        return getDatabase().getEmployee(id);
    }

    @Override
    public void updateEmployee(Employee employee) throws IOException {
        if (getDatabase().getEmployee(employee.getId()) != null) {

            getDatabase().putInEmployeesTable(employee);
        } else {
            display("Can't update, employee with id " + employee.getId() + " doesn't exist.");
        }
    }

    @Override
    public void deleteEmployee(int id) throws IOException {
        synchronized (getDatabase()) {
            if (Database.getTableLRUCache().snapshot().containsKey(id)) {
                getDatabase().removeFromTableCache(id);
            } else if (getDatabase().getEmployeesTable().containsKey(id)) {
                getDatabase().removeFromEmployeeTable(id);
            } else {
                display("Can't delete,employee with id " + id + " doesn't exist");
            }

        }
    }


    @Override
    public Map<Integer, Employee> filterByName(String name) throws IOException {

        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : getDatabase().getEmployeesTable().entrySet()) {
            if (employee.getValue().getName().toLowerCase().startsWith(name.toLowerCase())) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;

    }

    @Override
    public Map<Integer, Employee> filterBySalaryEQ(int salary) throws IOException {

        Map<Integer, Employee> employeeHashMap = new HashMap<>();

        for (Map.Entry<Integer, Employee> employee : getDatabase().getEmployeesTable().entrySet()) {
            if (employee.getValue().getSalary() == salary) {
                employeeHashMap.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashMap;


    }

    @Override
    public Map<Integer, Employee> filterBySalaryLT(int salary) throws IOException {


        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : getDatabase().getEmployeesTable().entrySet()) {
            if (employee.getValue().getSalary() < salary) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;
    }


    @Override
    public Map<Integer, Employee> filterBySalaryGT(int salary) throws IOException {

        Map<Integer, Employee> employeeHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Employee> employee : getDatabase().getEmployeesTable().entrySet()) {
            if (employee.getValue().getSalary() > salary) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
        return employeeHashtable;
    }

    @Override
    public void close() throws IOException {
        getDatabase().close();
    }

    @Override
    public List<Employee> selectAll() throws IOException {
        return new ArrayList<>(getDatabase().getEmployeesTable().values());
    }

}

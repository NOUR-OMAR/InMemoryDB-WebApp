package InMemoryDB.database.employee_table;

import InMemoryDB.model.Employee;
import InMemoryDB.database.Database;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static InMemoryDB.utils.Constant.Display.display;


@Data
@Service
public class EmployeeTableDAOImpl implements EmployeeTableDAO {


    private final Database database;



    private EmployeeTableDAOImpl() throws IOException {
        this.database = Database.getInitialisedDatabase();
    }



    @Override
    public void createEmployee(Employee employee) {
        if (getDatabase().getEmployee(employee.getId()) == null) {

          //  Employee newEmployee = new Employee(employee.getId(), employee.getName(), employee.getSalary(),employee.getDepartment().getId());
            getDatabase().putInEmployeesTable(employee);
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
    public void updateEmployee(Employee employee) {
        if (getDatabase().getEmployee(employee.getId()) != null) {
            //Employee newEmployee = new Employee(id, name, salary,
            // departmentId);
            getDatabase().putInEmployeesTable(employee);
           // return newEmployee;
        } else {
            display("Can't update, employee with id " + employee.getId() + " doesn't exist.");
           // return null;
        }
    }

    @Override
    public void deleteEmployee(int id) {
        synchronized (getDatabase()) {
            if (Database.getTableLRUCache().snapshot().containsKey(id)) {
                getDatabase().removeFromTableCache(id);
            } else if (Database.getAllEmployees().containsKey(id)) {
                getDatabase().removeFromEmployeeTable(id);
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
        Database.getInitialisedDatabase().close();
    }

    @Override
    public List<Employee> selectAll() {
        List<Employee> employees = new ArrayList<>();
       /* Map <Integer,Employee> employeeMap=getDatabase().getEmployeesTable();
        List <Integer>ids=new ArrayList<>(employeeMap.keySet());
        Collections.sort(ids, Comparator.comparingInt(a -> employeeMap.get(a).getId()));*/

        employees.addAll(getDatabase().getEmployeesTable().values());
        return employees;
    }

}

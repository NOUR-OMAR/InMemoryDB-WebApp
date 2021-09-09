package InMemoryDB.database;

import InMemoryDB.database.cache.LRUCache;
import InMemoryDB.database.storage.CSVFile;
import InMemoryDB.model.Department;
import InMemoryDB.model.Employee;
import InMemoryDB.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static InMemoryDB.utils.Constant.*;
import static InMemoryDB.utils.Constant.Display.display;


@Data
@Service
public class Database {
    @Setter
    private static Database database;
    CSVFile csvFile = new CSVFile();

    @Getter
    private static final LRUCache<Integer, Object> tableLRUCache = new LRUCache<>(CACHE_MAX_SIZE);

    @Getter
    private static final LRUCache<String, User> usersLRUCache = new LRUCache<>(CACHE_MAX_SIZE);

    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Employee> allEmployees;

    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Department> allDepartments;

    @Setter
    @Getter
    private static ConcurrentHashMap<String, User> allUsers;



    Database() throws IOException {
        setAllDepartments(new ConcurrentHashMap<>());
        setAllEmployees(new ConcurrentHashMap<>());
        setAllUsers(new ConcurrentHashMap<>());
        csvFile.initialize();
        writeInLogger();
        displayDepartmentsTable();
        displayEmployeeTable();
        displayUsers();
        displayCache();
    }

    public static synchronized Database getDatabase() throws IOException {
        if (database == null) {
            database = new Database();
        }
        return database;
    }


    public ConcurrentHashMap<Integer, Employee> getEmployeesTable() {


        return getAllEmployees();
    }

    public ConcurrentHashMap<Integer, Department> getDepartmentsTable() {

        return getAllDepartments();
    }
    public void displayUsers() {
        display("\n--- Users Table content: ---");
        getAllUsers().forEach((k, v) -> display(v.toString()));
        display("----------------------------------");
        display("File has " + CSVFile.getUsersCSVRowCount() + " rows. Loaded " + getAllUsers().size() + " data records.");
    }

    public void displayEmployeeTable() {
        display("\n--- Employees Table content: ---");
        getAllEmployees().forEach((k, v) -> display(v.toString()));
        display("----------------------------------");
        display("File has " + CSVFile.getEmployeesCSVRowCount() + " rows. Loaded " + getAllEmployees().size() + " data records.");
    }

    public void displayDepartmentsTable() {
        display("\n--- Departments Table content: ---");
        getAllDepartments().forEach((k, v) -> display(v.toString()));
        display("----------------------------------");
        display("File has " + CSVFile.getDepartmentsCSVRowCount() + " rows. Loaded " + getAllDepartments().size() + " data records.");
    }

    public void displayCache() {
        display("\n--- Cache content: ---");

        getTableLRUCache().snapshot().forEach((k, v) -> display(v.toString()));

        display("----------------------------------");
        display(getTableLRUCache().getMemorySize() + " data records in cache.");
    }

    public Employee getEmployee(Integer id) {
        if (getTableLRUCache().snapshot().containsKey(id)) {
            display("Cache hit");
            return (Employee) getTableLRUCache().get(id);
        } else if (getAllEmployees().containsKey(id)) {
            display("Cache miss");
            setTableLRUCache(id, getAllEmployees().get(id));
            return getAllEmployees().get(id);

        } else {
            display("Not found");
            return null;
        }
    }

    public Department getDepartment(Integer id) {
        if (getTableLRUCache().snapshot().containsKey(id)) {
            display("Cache hit");
            return (Department) getTableLRUCache().get(id);
        } else if (getAllDepartments().containsKey(id)) {
            display("Cache miss");
            setTableLRUCache(id, getAllDepartments().get(id));
            return getAllDepartments().get(id);

        } else {
            display("Not found");
            return null;
        }
    }

    public void putInEmployeesTable(Employee employee) throws IOException {
        synchronized (getTableLRUCache()) {


            for (Integer integer : getTableLRUCache().snapshot().keySet()) {
                getAllEmployees().put(integer, (Employee) Objects.requireNonNull(getTableLRUCache().get(integer)));
            }

            getAllEmployees().put(employee.getId(), employee);
            setTableLRUCache(employee.getId(), employee);

        }
        writeInLogger();
    }

    public void putInDepartmentsTable(Department department) throws IOException {
        synchronized (getTableLRUCache()) {

            for (Integer integer : getTableLRUCache().snapshot().keySet()) {
                getAllDepartments().put(integer, (Department) Objects.requireNonNull(getTableLRUCache().get(integer)));
            }
            getAllDepartments().put(department.getId(), department);
            setTableLRUCache(department.getId(), department);
        }

        writeInLogger();
    }

    public void putInUsersTable(User user) throws IOException {

        synchronized (getUsersLRUCache()) {

            for (String key : getUsersLRUCache().snapshot().keySet()) {
                getAllUsers().put(key, Objects.requireNonNull(getUsersLRUCache().get(key)));
            }
            getAllUsers().put(user.getUsername(), user);
            getUsersLRUCache().put(user.getUsername(), user);
        }

        writeInLogger();
    }

    public void removeFromTableCache(int id) throws IOException {
        synchronized (getTableLRUCache()) {
            getTableLRUCache().remove(id);
        }
        writeInLogger();
    }

    public void removeFromEmployeeTable(int id) throws IOException {
        synchronized (getAllEmployees()) {
            getAllEmployees().remove(id);
        }
        writeInLogger();
    }

    public void removeFromDepartmentsTable(int id) throws IOException {
        synchronized (getAllDepartments()) {
            getAllDepartments().remove(id);
        }
        writeInLogger();
    }

    public void close() throws IOException {

        csvFile.write(EMPLOYEES_CSV_PATH);
        csvFile.write(DEPARTMENTS_CSV_PATH);

    }

    public void closeUsersTB() throws IOException {
        csvFile.write(USERS_FILE_PATH);
    }

    public User getUser(String username) {

        return getAllUsers().get(username);

    }


    private void writeInLogger() throws IOException {
        csvFile.transactionLog.write(EMPLOYEES_LOGGER_FILE);
        csvFile.transactionLog.write(DEPARTMENTS_LOGGER_FILE);
        csvFile.transactionLog.write(USERS_LOGGER_FILE);
    }

    private void setTableLRUCache(int key, Object object) {

        getTableLRUCache().put(key, object);
    }


}



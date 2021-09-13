package InMemoryDB.database;

import InMemoryDB.database.cache.CacheMaxSize;
import InMemoryDB.database.cache.LRUCache;
import InMemoryDB.database.storage.CSVFile;
import InMemoryDB.database.storage.FilesPaths;
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

import static InMemoryDB.utils.Display.display;


@Data
@Service
public class Database {
    @Setter
    private static Database database;

    CSVFile csvFile = new CSVFile();

    @Getter
    private static final LRUCache<Integer, Object> tableLRUCache = new LRUCache<>(CacheMaxSize.CACHE_MAX_SIZE.getSize());

    @Getter
    private static final LRUCache<String, User> usersLRUCache = new LRUCache<>(CacheMaxSize.CACHE_MAX_SIZE.getSize());

    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Employee> allEmployees;

    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Department> allDepartments;

    @Setter
    @Getter
    private static ConcurrentHashMap<String, User> allUsers;

    private Database() throws IOException {
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
        try {
            writeInLogger();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            writeToCSV();
        }
    }

    public void putInDepartmentsTable(Department department) throws IOException {
        synchronized (getTableLRUCache()) {

            for (Integer integer : getTableLRUCache().snapshot().keySet()) {
                getAllDepartments().put(integer, (Department) Objects.requireNonNull(getTableLRUCache().get(integer)));
            }
            getAllDepartments().put(department.getId(), department);
            setTableLRUCache(department.getId(), department);
        }

        try {
            writeInLogger();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            writeToCSV();
        }
    }

    public void putInUsersTable(User user) throws IOException {

        synchronized (getUsersLRUCache()) {

            for (String key : getUsersLRUCache().snapshot().keySet()) {
                getAllUsers().put(key, Objects.requireNonNull(getUsersLRUCache().get(key)));
            }
            getAllUsers().put(user.getUsername(), user);
            getUsersLRUCache().put(user.getUsername(), user);
        }

        try {
            writeInLogger();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            writeToCSV();
        }
    }

    public void removeFromTableCache(int id) throws IOException {
        synchronized (getTableLRUCache()) {
            getTableLRUCache().remove(id);
        }
        try {
            writeInLogger();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            writeToCSV();
        }
    }

    public void removeFromEmployeeTable(int id) throws IOException {
        synchronized (getAllEmployees()) {
            getAllEmployees().remove(id);
        }
        try {
            writeInLogger();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            writeToCSV();
        }
    }

    public void removeFromDepartmentsTable(int id) throws IOException {
        synchronized (getAllDepartments()) {
            getAllDepartments().remove(id);
        }
        try {
            writeInLogger();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            writeToCSV();
        }
    }

    public void close() throws IOException {

        csvFile.write(FilesPaths.EMPLOYEES_CSV_PATH.getPath());
        csvFile.write(FilesPaths.DEPARTMENTS_CSV_PATH.getPath());

    }

    public void closeUsersTB() throws IOException {
        csvFile.write(FilesPaths.USERS_FILE_PATH.getPath());
    }

    public User getUser(String username) {

        return getAllUsers().get(username);

    }


    private void writeInLogger() throws IOException {
        csvFile.transactionLog.write(FilesPaths.EMPLOYEES_LOGGER_FILE.getPath());
        csvFile.transactionLog.write(FilesPaths.DEPARTMENTS_LOGGER_FILE.getPath());
        csvFile.transactionLog.write(FilesPaths.USERS_LOGGER_FILE.getPath());
    }

    private void writeToCSV() throws IOException {
        csvFile.transactionLog.writeToCSV();

    }

    private void setTableLRUCache(int key, Object object) {

        getTableLRUCache().put(key, object);
    }


}



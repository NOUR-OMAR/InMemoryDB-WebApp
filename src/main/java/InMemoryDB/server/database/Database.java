package InMemoryDB.server.database;

import InMemoryDB.client.model.Department;
import InMemoryDB.client.model.Employee;
import InMemoryDB.client.model.User;
import InMemoryDB.server.database.cache.LRUCache;
import InMemoryDB.server.database.storage.CSVFile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static InMemoryDB.utils.Constant.*;
import static InMemoryDB.utils.Constant.Display.display;


@Data
public class Database {

    /* @Getter
     private static final Map<Integer, Object> tableLRUCache = LRUHashMap.newInstance(MAX_SIZE);*/
    @Getter
    private static final LRUCache<Integer, Object> tableLRUCache = new LRUCache<>(MAX_SIZE);


    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Employee> allEmployees;
    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Department> allDepartments;


    private static final HashMap<String, User> allUsers = new HashMap<>();

    @Setter
    private static Database database;
    CSVFile csvFile = new CSVFile();

    Database() throws IOException {
        setAllDepartments(new ConcurrentHashMap<>());

        setAllEmployees(new ConcurrentHashMap<>());
        csvFile.initialize();
        writeInLogger();
        // displayDepartmentsTable();
        //displayEmployeeTable();
        displayCache();
    }

    public static synchronized Database getDatabase() throws IOException {
        if (database == null) {
            database = new Database();
        }
        return database;
    }


    public static synchronized Database getInitialisedDatabase() throws IOException {
        return Database.getDatabase();
    }

    public ConcurrentHashMap<Integer, Employee> selectAllEmployees() {

        return getAllEmployees();
    }

    public ConcurrentHashMap<Integer, Department> selectAllDepartments() {

        return getAllDepartments();
    }

    public static HashMap<String, User> getAllUsers() {
        return allUsers;

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
            // setEmployeeLRUCache(id, getAllEmployees().get(id));
            setTableLRUCache(id, getAllEmployees().get(id));
            return Database.getAllEmployees().get(id);

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
            return Database.getAllDepartments().get(id);

        } else {
            display("Not found");
            return null;
        }
    }

    public void putInEmployeesTable(Employee employee) {
        synchronized (getTableLRUCache()) {

            for (Integer integer : getTableLRUCache().snapshot().keySet()) {
                getAllEmployees().put(integer, (Employee) Objects.requireNonNull(getTableLRUCache().get(integer)));
            }
            getTableLRUCache().put(employee.getId(), employee);
        }
        writeInLogger();
    }

    public void putInDepartmentsTable(Department department) {
        synchronized (getTableLRUCache()) {

            for (Integer integer : getTableLRUCache().snapshot().keySet()) {
                getAllDepartments().put(integer, (Department) Objects.requireNonNull(getTableLRUCache().get(integer)));
            }
            // getAllDepartments().putAll((Map<? extends Integer, ? extends Department>) getTableLRUCache().values());
            getTableLRUCache().put(department.getId(), department);
        }

        writeInLogger();
    }

    public void removeFromTableCache(int id) {
        synchronized (getTableLRUCache()) {
            getTableLRUCache().remove(id);
        }
        writeInLogger();
    }

    public void removeFromEmployeeTable(int id) {
        synchronized (getAllEmployees()) {
            getAllEmployees().remove(id);
        }
        writeInLogger();
    }

    public void removeFromDepartmentsTable(int id) {
        synchronized (getAllDepartments()) {
            getAllDepartments().remove(id);
        }
        writeInLogger();
    }

    public void close() {

        csvFile.write(EMPLOYEES_CSV_PATH);
        csvFile.write(DEPARTMENTS_CSV_PATH);

    }

    public void closeUsersTB() {
        //StringBuilder stringBuilder = new StringBuilder();
        csvFile.write(USERS_FILE_PATH);
        // usersFile.tryWritingToFile(stringBuilder);

    }

    public User getUser(String username) {

        return getAllUsers().get(username);

    }

    public void putUser(User user) {
        synchronized (getAllUsers()) {
            getAllUsers().put(user.getUsername(), user);

        }
    }


    private void writeInLogger() {
        csvFile.transactionLog.write(EMPLOYEES_LOGGER_FILE);
        csvFile.transactionLog.write(DEPARTMENTS_LOGGER_FILE);
    }

    private void setTableLRUCache(int key, Object object) {

        getTableLRUCache().put(key, object);
    }


}



package InMemoryDB.server.database;

import InMemoryDB.client.model.Department;
import InMemoryDB.client.model.Employee;
import InMemoryDB.server.database.cache.LRUCache;
import InMemoryDB.server.database.storage.CSVFile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.MAX_SIZE;


@Data
public class Database {

    @Getter
    private static final Map<Integer, Object> tableLRUCache = LRUCache.newInstance(MAX_SIZE);


    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Employee> allEmployees;
    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Department> allDepartments;
    @Setter
    private static Database database;
    CSVFile csvFile = new CSVFile();

    Database() throws IOException {
        setAllDepartments(new ConcurrentHashMap<>());

        setAllEmployees(new ConcurrentHashMap<>());
        csvFile.initialize();
        csvFile.transactionLog.write();
        displayDepartmentsTable();
        displayEmployeeTable();
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


    public void displayCache() {
        display("\n--- Cache content: ---");
        getTableLRUCache().forEach((k, v) -> display(v.toString()));
        // getEmployeesLRUCache().forEach((k, v) -> display(v.toString()));
        //getDepartmentsLRUCache().forEach((k, v) -> display(v.toString()));
        //    display("----------------------------------");
        //   display("In-Memory Database has " + CSVFile.getEmployeesCSVRowCount() + " rows. Loaded " + getTablesLRUCache().size() + " data records in server.database.cache.");
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

    public Employee getEmployee(Integer id) {
        if (getTableLRUCache().containsKey(id)) {
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
        if (getTableLRUCache().containsKey(id)) {
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

            for (Integer integer : getTableLRUCache().keySet()) {
                getAllEmployees().put(integer, (Employee) getTableLRUCache().get(integer));
            }
            // getAllEmployees().putAll((Map<? extends Integer, ? extends Employee>) getTableLRUCache().values());
            getTableLRUCache().put(employee.getId(), employee);
        }

        csvFile.transactionLog.write();
    }

    public void putInDepartmentsTable(Department department) {
        synchronized (getTableLRUCache()) {

            for (Integer integer : getTableLRUCache().keySet()) {
                getAllDepartments().put(integer, (Department) getTableLRUCache().get(integer));
            }
            // getAllDepartments().putAll((Map<? extends Integer, ? extends Department>) getTableLRUCache().values());
            getTableLRUCache().put(department.getId(), department);
        }

        csvFile.transactionLog.write();
    }


    public void removeFromTableCache(int id) {
        synchronized (getTableLRUCache()) {
            getTableLRUCache().remove(id);
        }
        csvFile.transactionLog.write();//
    }

   /* public void removeFromDepartmentCache(int id) {
        synchronized (getDepartmentsLRUCache()) {
            getDepartmentsLRUCache().remove(id);
        }
        csvFile.transactionLog.write();
    }*/

    public void removeFromEmployeeTable(int id) {
        synchronized (getAllEmployees()) {
            getAllEmployees().remove(id);
        }
        csvFile.transactionLog.write();
    }


    public void removeFromDepartmentsTable(int id) {
        synchronized (getAllDepartments()) {
            getAllDepartments().remove(id);
        }
        csvFile.transactionLog.write();//
    }


    public void close() {
        StringBuilder stringBuilder = new StringBuilder();
        csvFile.setEmployeeRecordStringBuilder(stringBuilder);
        csvFile.tryWritingToEmployeesFile(stringBuilder);
        csvFile.setDepartmentRecordStringBuilder(stringBuilder);
        csvFile.tryWritingToEmployeesFile(stringBuilder);

    }

    private void setTableLRUCache(int key, Object object) {

        getTableLRUCache().putIfAbsent(key, object);
    }

    /*private void setDepartmentsLRUCache(int key, Department department) {

        getDepartmentsLRUCache().putIfAbsent(key, department);
    }
*/


}



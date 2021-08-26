package InMemoryDB.server.database;

import InMemoryDB.client.model.Employee;
import InMemoryDB.server.database.cache.LRUCache;
import InMemoryDB.server.database.storage.CSVFile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.MAX_SIZE;


@Data
public class Database {

    @Getter
    private static final Map<Integer, Employee> employeeLRUCache = Collections.synchronizedMap(LRUCache.newInstance(MAX_SIZE));
    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Employee> allEmployees;
    @Setter
    private static Database database;
    CSVFile csvFile = new CSVFile();

    Database() throws IOException {
        setAllEmployees(new ConcurrentHashMap<>());
        csvFile.initialize();
        displayDB();
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

    public ConcurrentHashMap<Integer, Employee> selectAll() {

        return Database.getAllEmployees();
    }


    public void displayCache() {
        display("\n--- Cache content: ---");
        getEmployeeLRUCache().forEach((k, v) -> display(v.toString()));
        display("----------------------------------");
        display("In-Memory Database has " + CSVFile.getRowCount() + " rows. Loaded " + getEmployeeLRUCache().size() + " data records in server.database.cache.");
    }

    public void displayDB() {
        display("\n--- Database content: ---");
        getAllEmployees().forEach((k, v) -> display(v.toString()));
        display("----------------------------------");
        display("File has " + CSVFile.getRowCount() + " rows. Loaded " + getAllEmployees().size() + " data records.");
    }

    public Employee get(Integer id) {
        if (getEmployeeLRUCache().containsKey(id)) {
            display("Cache hit");
            return getEmployeeLRUCache().get(id);
        } else if (getAllEmployees().containsKey(id)) {
            display("Cache miss");
            setEmployeeLRUCache(id, getAllEmployees().get(id));
            return Database.getAllEmployees().get(id);

        } else {
            display("Not found");
            return null;
        }
    }

    public void put(Employee employee) {
        synchronized (getEmployeeLRUCache()) {
            getAllEmployees().putAll(getEmployeeLRUCache());
            getEmployeeLRUCache().put(employee.getId(), employee);
        }

        csvFile.transactionLog.write();
    }


    public void removeFromCache(int id) {
        synchronized (getEmployeeLRUCache()) {
            getEmployeeLRUCache().remove(id);
        }
        csvFile.transactionLog.write();
    }

    public void removeFromTable(int id) {
        synchronized (getAllEmployees()) {
            getAllEmployees().remove(id);
        }
        csvFile.transactionLog.write();
    }


    public void close() {
        StringBuilder stringBuilder = new StringBuilder();
        csvFile.setStringBuilder(stringBuilder);
        csvFile.tryWritingToFile(stringBuilder);

    }

    private void setEmployeeLRUCache(int key, Employee employee) {

        getEmployeeLRUCache().putIfAbsent(key, employee);
    }
}



package InMemoryDB.server.database.departments_table;

import InMemoryDB.client.model.Department;
import InMemoryDB.server.database.Database;
import lombok.Data;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static InMemoryDB.server.database.Database.getInitialisedDatabase;
import static InMemoryDB.utils.Constant.Display.display;


@Data
public class DepartmentsTableDAOImpl implements DepartmentsTableDAO {


    private final Database database;


    public DepartmentsTableDAOImpl(Database database) {
        this.database = database;
    }

    private DepartmentsTableDAOImpl() throws IOException {
        this.database = getInitialisedDatabase();
    }

    public static DepartmentsTableDAO createEmployeeDAOTableImpl() throws IOException {
        return new DepartmentsTableDAOImpl();
    }


    @Override
    public Department createDepartment(String id, String name, String location) {
        if (getDatabase().getEmployee(Integer.parseInt(id)) == null) {
            Department newDepartment = new Department(id, name, location);
            getDatabase().putInDepartmentsTable(newDepartment);
            return newDepartment;
        } else {
            display("Can't create, department with id " + id + " already existed.");
            return null;
        }
    }

    @Override
    public Department readDepartment(String id) {
        return getDatabase().getDepartment(Integer.parseInt(id));
    }

    @Override
    public Department updateDepartment(String id, String name, String location) {
        if (getDatabase().getEmployee(Integer.parseInt(id)) != null) {
            Department newDepartment = new Department(id, name, location);
            getDatabase().putInDepartmentsTable(newDepartment);
            return newDepartment;
        } else {
            display("Can't update, department with id " + id + " doesn't exist.");
            return null;
        }
    }

    @Override //
    public void deleteDepartment(String id) {
        synchronized (getDatabase()) {
            if (Database.getTableLRUCache().snapshot().containsKey(Integer.parseInt(id))) {
                getDatabase().removeFromTableCache(Integer.parseInt(id));
            } else if (Database.getAllDepartments().containsKey(Integer.parseInt(id))) {
                getDatabase().removeFromDepartmentsTable(Integer.parseInt(id));
            } else {
                display("Can't delete,department with id " + id + " doesn't exist");
            }

        }
    }


    @Override
    public Map<Integer, Department> filterByName(String name) {

        Map<Integer, Department> departmentHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Department> department : Database.getAllDepartments().entrySet()) {
            if (department.getValue().getName().toLowerCase().startsWith(name.toLowerCase())) {
                departmentHashtable.put(department.getKey(), department.getValue());
            }
        }
        return departmentHashtable;

    }

    @Override
    public Map<Integer, Department> filterByLocation(String location) {

        Map<Integer, Department> departmentHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Department> department : Database.getAllDepartments().entrySet()) {
            if (department.getValue().getName().toLowerCase().startsWith(location.toLowerCase())) {
                departmentHashtable.put(department.getKey(), department.getValue());
            }
        }
        return departmentHashtable;

    }


    @Override
    public void close() throws IOException {
        getInitialisedDatabase().close();
    }

    @Override
    public String selectAll() {
        return getDatabase().selectAllDepartments().values().toString();
    }

}

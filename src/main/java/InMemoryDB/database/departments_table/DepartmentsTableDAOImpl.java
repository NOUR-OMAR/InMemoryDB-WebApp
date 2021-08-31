package InMemoryDB.database.departments_table;

import InMemoryDB.model.Department;
import InMemoryDB.database.Database;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static InMemoryDB.utils.Constant.Display.display;


@Data
@Service
public class DepartmentsTableDAOImpl implements DepartmentsTableDAO {


    private final Database database;


    public DepartmentsTableDAOImpl(Database database) {
        this.database = database;
    }

    private DepartmentsTableDAOImpl() throws IOException {
        this.database = Database.getInitialisedDatabase();
    }

    public static DepartmentsTableDAO createEmployeeDAOTableImpl() throws IOException {
        return new DepartmentsTableDAOImpl();
    }


    @Override
    public void createDepartment(Department department) {
        if (getDatabase().getDepartment(department.getId()) == null) {

            getDatabase().putInDepartmentsTable(department);

        } else {
            display("Can't create, department with id " + department.getId() + " already existed.");
        }
    }

    @Override
    public Department readDepartment(int id) {
        return getDatabase().getDepartment(id);
    }

    @Override
    public void updateDepartment(Department department) {
        if (getDatabase().getDepartment(department.getId()) != null) {

            getDatabase().putInDepartmentsTable(department);

        } else {
            display("Can't create, department with id " + department.getId() + " already existed.");
        }
    }


    @Override //
    public void deleteDepartment(int id) {
        synchronized (getDatabase()) {
            if (Database.getTableLRUCache().snapshot().containsKey(id)) {
                getDatabase().removeFromTableCache(id);
            } else if (Database.getAllDepartments().containsKey(id)) {
                getDatabase().removeFromDepartmentsTable(id);
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
            if (department.getValue().getLocation().toLowerCase().startsWith(location.toLowerCase())) {
                departmentHashtable.put(department.getKey(), department.getValue());
            }
        }
        return departmentHashtable;

    }


    @Override
    public void close() throws IOException {
        Database.getInitialisedDatabase().close();
    }

    @Override
    public List<Department> selectAll() {

        List<Department> departments = new ArrayList<>();
        for (Department department : getDatabase().getDepartmentsTable().values())
            departments.add(department);
        return departments;
    }

}

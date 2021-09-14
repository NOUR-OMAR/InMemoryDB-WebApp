package InMemoryDB.database.departments_table;

import InMemoryDB.database.Database;
import InMemoryDB.model.Department;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static InMemoryDB.database.Database.getDatabase;
import static InMemoryDB.utils.Display.display;


@Data
@Component
public class DepartmentsTableDAOImpl implements DepartmentsTableDAO {

    @Override
    public void createDepartment(Department department) throws IOException {
        if (getDatabase().getDepartment(department.getId()) == null) {

            getDatabase().putInDepartmentsTable(department);

        } else {
            display("Can't create, department with id " + department.getId() + " already existed.");
        }
    }

    @Override
    public Department readDepartment(int id) throws IOException {
        return getDatabase().getDepartment(id);
    }

    @Override
    public void updateDepartment(Department department) throws IOException {
        if (getDatabase().getDepartment(department.getId()) != null) {

            getDatabase().putInDepartmentsTable(department);

        } else {
            display("Can't create, department with id " + department.getId() + " already existed.");
        }
    }


    @Override
    public void deleteDepartment(int id) throws IOException {
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
    public Map<Integer, Department> filterByName(String name) throws IOException {

        Map<Integer, Department> departmentHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Department> department : getDatabase().getDepartmentsTable().entrySet()) {
            if (department.getValue().getName().toLowerCase().startsWith(name.toLowerCase())) {
                departmentHashtable.put(department.getKey(), department.getValue());
            }
        }
        return departmentHashtable;

    }

    @Override
    public Map<Integer, Department> filterByLocation(String location) throws IOException {

        Map<Integer, Department> departmentHashtable = new Hashtable<>();

        for (Map.Entry<Integer, Department> department : getDatabase().getDepartmentsTable().entrySet()) {
            if (department.getValue().getLocation().toLowerCase().startsWith(location.toLowerCase())) {
                departmentHashtable.put(department.getKey(), department.getValue());
            }
        }
        return departmentHashtable;

    }


    @Override
    public void close() throws IOException {
        getDatabase().close();
    }

    @Override
    public List<Department> selectAll() throws IOException {

        return new ArrayList<>(getDatabase().getDepartmentsTable().values());
    }

}

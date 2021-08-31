package InMemoryDB.database.departments_table;

import InMemoryDB.model.Department;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//DAO (Data Access Object) class
//that provides CRUD (Create, Read, Update, Delete) operations as well as other operations for the table Departments in the server.database.
public interface DepartmentsTableDAO {
    void createDepartment(Department department) throws IOException;

    void updateDepartment(Department department);

    void deleteDepartment(int id);

    Department readDepartment(int id);

    Map<Integer, Department> filterByName(String name);

    Map<Integer, Department> filterByLocation(String location);

    void close() throws IOException;

    List<Department> selectAll();

}

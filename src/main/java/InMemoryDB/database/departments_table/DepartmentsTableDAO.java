package InMemoryDB.database.departments_table;

import InMemoryDB.model.Department;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//DAO (Data Access Object) class
//that provides CRUD (Create, Read, Update, Delete) operations as well as other operations for the table Departments in the server.database.
public interface DepartmentsTableDAO {
    void createDepartment(Department department) throws IOException;

    void updateDepartment(Department department) throws IOException;

    void deleteDepartment(int id) throws IOException;

    Department readDepartment(int id) throws IOException;

    Map<Integer, Department> filterByName(String name) throws IOException;

    Map<Integer, Department> filterByLocation(String location) throws IOException;

    void close() throws IOException;

    List<Department> selectAll() throws IOException;

}

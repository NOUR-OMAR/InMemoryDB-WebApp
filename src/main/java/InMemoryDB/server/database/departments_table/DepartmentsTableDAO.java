package InMemoryDB.server.database.departments_table;

import InMemoryDB.client.model.Department;

import java.io.IOException;
import java.util.Map;

//DAO (Data Access Object) class
//that provides CRUD (Create, Read, Update, Delete) operations as well as other operations for the table Departments in the server.database.
public interface DepartmentsTableDAO {
    Department createDepartment(String id, String name, String location) throws IOException;

    Department updateDepartment(String id, String name, String location);

    void deleteDepartment(String id);

    Department readDepartment(String id);

    Map<Integer, Department> filterByName(String name);

    Map<Integer, Department> filterByLocation(String location);

    void close() throws IOException;

    String selectAll();

}

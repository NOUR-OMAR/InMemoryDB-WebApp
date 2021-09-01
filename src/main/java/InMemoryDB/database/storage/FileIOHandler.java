package InMemoryDB.database.storage;

import InMemoryDB.model.Department;
import InMemoryDB.model.Employee;
import InMemoryDB.model.User;
import InMemoryDB.database.Database;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static InMemoryDB.utils.Constant.Display.display;

public class FileIOHandler {

    public static String toDepartmentRecord(Department department) {
        return department.getId() + ";" +
                department.getName() + ";" +
                department.getLocation() + "\n";
    }
    @Nullable
    public static String toEmployeeRecord(Employee employee) {
        if (employee.getDepartment()==null)
         employee.setDepartment(new Department());
        return employee.getId() + ";" +
                employee.getName() + ";" +
                employee.getSalary() + ";" +
                employee.getDepartment().getId() + "\n";
    }

    protected static void buildEmployeeRecordString(StringBuilder stringBuilder) {

        for (Integer integer : Database.getTableLRUCache().snapshot().keySet()) {
            if (Database.getTableLRUCache().snapshot() instanceof Employee)

                Database.getAllEmployees().put(integer, (Employee) Objects.requireNonNull(Database.getTableLRUCache().get(integer)));
        }

        for (Map.Entry<Integer, Employee> entry : Database.getAllEmployees().entrySet()) {
            Employee employee = entry.getValue();
            stringBuilder.append(toEmployeeRecord(employee));
        }
    }

    protected static void buildDepartmentRecordString(StringBuilder stringBuilder) {

        for (Integer integer : Database.getTableLRUCache().snapshot().keySet()) {
            if (Database.getTableLRUCache().snapshot() instanceof Department)

                Database.getAllDepartments().put(integer, (Department) Objects.requireNonNull(Database.getTableLRUCache().get(integer)));
        }

        for (Map.Entry<Integer, Department> entry : Database.getAllDepartments().entrySet()) {
            Department department = entry.getValue();
            stringBuilder.append(toDepartmentRecord(department));
        }
    }

    protected static void buildUsersRecordString(StringBuilder stringBuilder) {
        for (Map.Entry<String, User> entry : Database.getAllUsers().entrySet()) {
            User user = entry.getValue();
            stringBuilder.append(user.getUsername()).append(";").append(user.getPassword()).append(";").append(user.getRole()).append("\n");
        }
    }

    public void tryWritingToFile(StringBuilder stringBuilder, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath, false)) {
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();

            display("Data saved to file: " + filePath);
        } catch (IOException ignored) {
            display("Error: Saving the data is failed!");
        }
    }


}

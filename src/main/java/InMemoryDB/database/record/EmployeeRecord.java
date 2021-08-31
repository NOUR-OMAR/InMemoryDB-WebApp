package InMemoryDB.database.record;


import InMemoryDB.model.Department;
import InMemoryDB.model.Employee;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.EMPLOYEES_RECORD_LENGTH;


public class EmployeeRecord implements RecordHandler {

    private static Employee employee;


    private static void tryParsingRecord(String record) {
        try {
            setEmployeeRecord(record, employee);
        } catch (IllegalArgumentException illegalArgumentException) {
            display(illegalArgumentException.getMessage() + "\n RecordHandler \"" + record + "\" was not parsed.");
            illegalArgumentException.printStackTrace();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            display("\n RecordHandler \"" + record + "\" has wrong structure and was not parsed.");
            indexOutOfBoundsException.printStackTrace();
        }
    }

    private static void setEmployeeRecord(String record, Employee employee) {
        String[] employeeRecord = record.split(";");//
        if (employeeRecord.length > EMPLOYEES_RECORD_LENGTH) throw new IndexOutOfBoundsException();
        employee.setId(Integer.parseInt(employeeRecord[0]));
        employee.setName(employeeRecord[1]);
        employee.setSalary(Integer.parseInt(employeeRecord[2]));
        employee.setDepartment(Department.departments.get(Integer.parseInt(employeeRecord[3])));
      //  Employee.employeesDepartments.put(employee,Department.departments.get(employeeRecord[3]));
    }

    @Override
    public Employee parseRecord(String record) throws IllegalArgumentException {//TODO was static
        employee = new Employee();
        tryParsingRecord(record);
        return employee;
    }


}

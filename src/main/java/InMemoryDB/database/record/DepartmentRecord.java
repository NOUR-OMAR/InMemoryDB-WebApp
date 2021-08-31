package InMemoryDB.database.record;

import InMemoryDB.model.Department;

import static InMemoryDB.utils.Constant.DEPARTMENT_RECORD_LENGTH;
import static InMemoryDB.utils.Constant.Display.display;

public class DepartmentRecord implements RecordHandler {
    private static Department department;


    private static void tryParsingRecord(String record) {
        try {
            setDepartmentRecord(record, department);
        } catch (IllegalArgumentException illegalArgumentException) {
            display(illegalArgumentException.getMessage() + "\n RecordHandler \"" + record + "\" was not parsed.");
            illegalArgumentException.printStackTrace();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            display("\n RecordHandler \"" + record + "\" has wrong structure and was not parsed.");
            indexOutOfBoundsException.printStackTrace();
        }
    }

    private static void setDepartmentRecord(String record, Department department) {
        String[] departmentRecord = record.split(";");//
        if (departmentRecord.length > DEPARTMENT_RECORD_LENGTH) throw new IndexOutOfBoundsException();
        department.setId(Integer.parseInt(departmentRecord[0]));
        department.setName(departmentRecord[1]);
        department.setLocation(departmentRecord[2]);
        Department.departments.put(Integer.parseInt(departmentRecord[0]), department);
//display(String.valueOf(Department.departments));
    }

    @Override
    public Department parseRecord(String record) throws IllegalArgumentException {//TODO was static
        department = new Department();
        tryParsingRecord(record);
        return department;
    }


}

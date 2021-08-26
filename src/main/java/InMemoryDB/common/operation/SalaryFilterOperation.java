package InMemoryDB.common.operation;


import InMemoryDB.client.model.Employee;
import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static InMemoryDB.common.operation.OperationTypes.FilterSalary;
import static InMemoryDB.utils.Constant.*;
import static InMemoryDB.utils.Constant.SalaryCondition.*;

public class SalaryFilterOperation extends AbstractOperation {

    private SalaryFilterOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static SalaryFilterOperation createSalaryFilterOperation(String[] parameters) throws IOException {
        return new SalaryFilterOperation(parameters);
    }

    @Override
    public void run() {
        runOperation();
    }

    @Override
    public String runOperation() {
        ArrayList<String> salaries = new ArrayList<>();
        if (isCommandValid()) {
            doDelay();
            String relation = parameters[0].toLowerCase();
            int salary;
            try {
                salary = Integer.parseInt(parameters[1]);
                if (salary < MIN_SALARY || salary > MAX_SALARY) {
                    //TODO null handling
                    return new ResponseBuilder().createResponse().buildResponse(SALARY_ERROR_MESSAGE, FilterSalary);

                }
            } catch (Exception exception) {


                exception.printStackTrace();
                return null;
            }
            Map<Integer, Employee> filtered;
            switch (relation) {
                case LESS_THAN -> filtered = employeeTableDAO.filterBySalaryLT(salary);
                case GREATER_THAN -> filtered = employeeTableDAO.filterBySalaryGT(salary);
                case EQUALS -> filtered = employeeTableDAO.filterBySalaryEQ(salary);
                default -> {
                    return new ResponseBuilder().createResponse().buildResponse("Not supported", FilterSalary);

                }
            }

            filtered.forEach((k, v) -> salaries.add(v.toString()));
        } else {
            return new ResponseBuilder().createResponse().buildResponse(ERROR_MESSAGE, FilterSalary);

        }
        if (salaries.isEmpty()) {
            return new ResponseBuilder().createResponse().buildResponse("not found ", FilterSalary);

        }
        return new ResponseBuilder().createResponse().buildResponse(salaries.toString(), FilterSalary);
    }

    private boolean isCommandValid() {
        return parameters.length >= 2;
    }


}

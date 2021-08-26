package InMemoryDB.common.operation;


import InMemoryDB.client.model.Employee;
import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static InMemoryDB.common.operation.OperationTypes.FilterName;
import static InMemoryDB.utils.Constant.ERROR_MESSAGE;

public class NameFilterOperation extends AbstractOperation {

    private NameFilterOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static NameFilterOperation createNameFilterOperation(String[] parameters) throws IOException {
        return new NameFilterOperation(parameters);
    }

    @Override
    public void run() {
        runOperation();
    }

    @Override
    public String runOperation() {
        ArrayList<String> names = new ArrayList<>();

        if (isCommandValid()) {
            doDelay();
            Map<Integer, Employee> filtered = employeeTableDAO.filterByName(parameters[0]);

            filtered.forEach((k, v) -> names.add(v.toString()));
//TODO handle null
        } else {
            return new ResponseBuilder().createResponse().buildResponse(ERROR_MESSAGE, FilterName);
        }
        if (names.isEmpty()) {
            return new ResponseBuilder().createResponse().buildResponse(parameters[0] + " not found", FilterName);

        }
        return new ResponseBuilder().createResponse().buildResponse(names.toString(), FilterName);

    }

    private boolean isCommandValid() {
        return parameters.length >= 1;
    }


}

package InMemoryDB.common.operation.crud;

import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.common.operation.AbstractOperation;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;
import lombok.val;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.Create;
import static InMemoryDB.utils.Constant.ERROR_MESSAGE;
import static InMemoryDB.utils.Constant.NULL;

public class CreateOperation extends AbstractOperation {

    private CreateOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static CreateOperation createCreateOperation(String[] parameters) throws IOException {
        return new CreateOperation(parameters);
    }

    @Override
    public void run() {

        try {
            runOperation();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    @Override
    public String runOperation() throws IOException {
        if (isCommandValid()) {
            doDelay();
            val employee = employeeTableDAO.createEmployee(parameters[0], parameters[1], parameters[2]);
            if (employee == null)
                return new ResponseBuilder().createResponse().buildResponse(NULL, Create);
            else
                return new ResponseBuilder().createResponse().buildEmpResponse(employee, Create);
        } else {
            return new ResponseBuilder().createResponse().buildResponse(ERROR_MESSAGE, Create);
        }
    }


    private boolean isCommandValid() {
        return parameters.length >= 3;
    }


}

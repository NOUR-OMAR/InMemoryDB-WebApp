package InMemoryDB.common.operation.crud;

import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.common.operation.AbstractOperation;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;
import lombok.val;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.Update;
import static InMemoryDB.utils.Constant.ERROR_MESSAGE;
import static InMemoryDB.utils.Constant.NULL;

public class UpdateOperation extends AbstractOperation {

    private UpdateOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static UpdateOperation createUpdateOperation(String[] parameters) throws IOException {
        return new UpdateOperation(parameters);
    }

    @Override
    public void run() {
        runOperation();
    }

    @Override
    public String runOperation() {
        if (isCommandValid()) {
            doDelay();
            val updatedEmployee = employeeTableDAO.updateEmployee(parameters[0], parameters[1], parameters[2]);
            if (updatedEmployee == null)
                return new ResponseBuilder().createResponse().buildResponse(NULL, Update);
            else
                return new ResponseBuilder().createResponse().buildEmpResponse(updatedEmployee, Update);

        } else {
            return new ResponseBuilder().createResponse().buildResponse(ERROR_MESSAGE, Update);
        }
    }

    private boolean isCommandValid() {
        return parameters.length >= 3;
    }


}

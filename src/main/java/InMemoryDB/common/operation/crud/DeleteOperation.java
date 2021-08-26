package InMemoryDB.common.operation.crud;


import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.common.operation.AbstractOperation;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.Delete;
import static InMemoryDB.utils.Constant.ERROR_MESSAGE;

public class DeleteOperation extends AbstractOperation {


    private DeleteOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static DeleteOperation createDeleteOperation(String[] parameters) throws IOException {
        return new DeleteOperation(parameters);
    }

    @Override
    public void run() {
        runOperation();
    }

    @Override
    public String runOperation() {
        if (isCommandValid()) {
            doDelay();
            employeeTableDAO.deleteEmployee(parameters[0]);
            return new ResponseBuilder().createResponse().buildResponse(parameters[0], Delete);

        } else {
            return new ResponseBuilder().createResponse().buildResponse(ERROR_MESSAGE, Delete);

        }
    }

    private boolean isCommandValid() {
        return parameters.length >= 1;
    }

}

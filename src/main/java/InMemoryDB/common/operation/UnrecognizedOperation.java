package InMemoryDB.common.operation;

import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.UNRECOGNIZED;
import static InMemoryDB.utils.Constant.ERROR_MESSAGE;

public class UnrecognizedOperation extends AbstractOperation {

    private UnrecognizedOperation() throws IOException {
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static UnrecognizedOperation createUnrecognizedOperation() throws IOException {
        return new UnrecognizedOperation();
    }

    @Override
    public void run() {
        runOperation();
    }

    @Override
    public String runOperation() {
        if (isCommandValid())

            return new ResponseBuilder().createResponse().buildResponse("Unrecognized Operation Types", UNRECOGNIZED);

        else
            return new ResponseBuilder().createResponse().buildResponse(ERROR_MESSAGE, UNRECOGNIZED);

    }

    private boolean isCommandValid() {
        return true;
    }

}

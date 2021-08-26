package InMemoryDB.common.operation;

import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.Close;


public class CloseOperation extends AbstractOperation {

    private CloseOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static CloseOperation createCloseOperation(String[] parameters) throws IOException {
        return new CloseOperation(parameters);
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
            employeeTableDAO.close();
        }
        return new ResponseBuilder().createResponse().buildResponse("closed and saved!", Close);

    }

    private boolean isCommandValid() {
        return true;
    }

}

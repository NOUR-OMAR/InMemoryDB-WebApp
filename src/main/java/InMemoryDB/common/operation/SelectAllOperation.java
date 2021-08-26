package InMemoryDB.common.operation;


import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.SelectAll;

public class SelectAllOperation extends AbstractOperation {

    private SelectAllOperation() throws IOException {
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static SelectAllOperation createSelectAllOperation() throws IOException {
        return new SelectAllOperation();
    }

    @Override
    public void run() {
        runOperation();
    }

    @Override
    public String runOperation() {
        if (isCommandValid()) {
            doDelay();
        }
        return new ResponseBuilder().createResponse().buildResponse(employeeTableDAO.selectAll(), SelectAll);
    }

    private boolean isCommandValid() {
        return true;
    }


}

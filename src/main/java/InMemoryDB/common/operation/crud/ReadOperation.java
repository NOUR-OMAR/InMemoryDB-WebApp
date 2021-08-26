package InMemoryDB.common.operation.crud;


import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.common.operation.AbstractOperation;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;
import lombok.val;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.Read;
import static InMemoryDB.utils.Constant.ERROR_MESSAGE;
import static InMemoryDB.utils.Constant.NULL;

public class ReadOperation extends AbstractOperation {


    public ReadOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static ReadOperation createReadOperation(String[] parameters) throws IOException {
        return new ReadOperation(parameters);
    }

    @Override
    public void run() {
        runOperation();
    }

    @Override
    public String runOperation() {
        if (isCommandValid()) {
            doDelay();

            val readEmployee = employeeTableDAO.readEmployee(parameters[0]);
            if (readEmployee == null)
                return new ResponseBuilder().createResponse().buildResponse(NULL, Read);
            else
                return new ResponseBuilder().createResponse().buildEmpResponse(readEmployee, Read);

        } else {

            return new ResponseBuilder().createResponse().buildResponse(ERROR_MESSAGE, Read);
        }

    }

    private boolean isCommandValid() {
        return parameters.length >= 1;
    }
}
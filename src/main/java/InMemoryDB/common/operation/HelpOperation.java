package InMemoryDB.common.operation;

import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.Help;
import static InMemoryDB.utils.Constant.HELP_MESSAGE;

@EqualsAndHashCode(callSuper = true)
@Data
public class HelpOperation extends AbstractOperation {

    private HelpOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static HelpOperation createHelpOperation(String[] parameters) throws IOException {
        return new HelpOperation(parameters);
    }

    @Override
    public void run() {
        runOperation();
    }

    @Override
    public String runOperation() {
        String help = "";
        if (isCommandValid()) {


            help = HELP_MESSAGE;
        }

        return new ResponseBuilder().createResponse().buildResponse(help, Help);

    }


    private boolean isCommandValid() {
        return true;
    }

}

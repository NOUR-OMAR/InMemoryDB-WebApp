package InMemoryDB.common.operation.crud;

import InMemoryDB.client.model.Employee;
import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.common.operation.AbstractOperation;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.Create;
import static InMemoryDB.utils.Constant.ERROR_MESSAGE;
import static InMemoryDB.utils.Constant.NULL;

@Controller
@RequestMapping(value = "/CreateEmployee", method = RequestMethod.POST)
public class CreateOperation extends AbstractOperation {

    private CreateOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static CreateOperation createCreateOperation(@RequestParam int id, @RequestParam String name, @RequestParam String salary, ModelMap model) throws IOException {


        String[] parameters = new String[3];
        parameters[0] = String.valueOf(id);
        parameters[1] = name;
        parameters[2] = salary;

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
            // Employee employee = employeeTableDAO.createEmployee(parameters[0], parameters[1], parameters[2]);
            Employee employee = employeeTableDAO.createEmployee(parameters[0], parameters[1], parameters[2]);
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

package InMemoryDB.common.operation.crud;


import InMemoryDB.common.ResponseBuilder;
import InMemoryDB.server.database.employee_table.EmployeeTableDAO;
import InMemoryDB.server.database.employee_table.EmployeeTableDAOImpl;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

import static InMemoryDB.common.operation.OperationTypes.Read;
import static InMemoryDB.utils.Constant.ERROR_MESSAGE;
import static InMemoryDB.utils.Constant.NULL;


@Controller
public class ReadOperation {
    protected String[] parameters;
    protected EmployeeTableDAO employeeTableDAO;

   /* public ReadOperation(String[] parameters) throws IOException {
        this.parameters = parameters;
        employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();
    }

    public static ReadOperation createReadOperation() throws IOException {
        //String [] parameters=new String[1];
        //parameters[0]=String.valueOf(id);
        return new ReadOperation(parameters);
    }*/


    @RequestMapping(value = "/ReadEmployee/{id}", method = RequestMethod.GET)
    public String runOperation(@PathVariable("id") int id) throws IOException {
        if (isCommandValid()) {
            //    doDelay();
            employeeTableDAO = EmployeeTableDAOImpl.createEmployeeDAOTableImpl();

            val readEmployee = employeeTableDAO.readEmployee(String.valueOf(id));
            if (readEmployee == null)
                return new ResponseBuilder().createResponse().buildResponse(NULL, Read);
            else
                // return new ResponseBuilder().createResponse().buildEmpResponse(readEmployee, Read);
                return readEmployee.toString();

        } else {

            return new ResponseBuilder().createResponse().buildResponse(ERROR_MESSAGE, Read);
        }

    }

    private boolean isCommandValid() {
        return parameters.length >= 1;
    }
}
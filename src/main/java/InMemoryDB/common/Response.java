package InMemoryDB.common;

import InMemoryDB.client.model.Employee;
import InMemoryDB.common.operation.OperationTypes;
import lombok.Data;

import javax.json.Json;

@Data
public class Response {
    private Employee employee;
    private OperationTypes operation;
    private String body;


    public String buildEmpResponse(Employee employee, OperationTypes operation) {

        return buildEmployeeResponse(operation.toString(), employee);

    }

    public String buildResponse(String body, OperationTypes operation) {

        return buildResponse(operation.toString(), body);

    }


    private String buildEmployeeResponse(String operation, Employee employee) {
        return Json.createObjectBuilder()
                .add("OperationTypes", operation)
                .add("Employee", Json.createObjectBuilder()
                        .add("Id", employee.getId())
                        .add("name", employee.getName())
                        .add("salary", employee.getSalary()))
                .build()
                .toString();
    }

    private String buildResponse(String operation, String body) {
        return Json.createObjectBuilder()
                .add("OperationTypes", operation)
                .add("body", body)
                .build()
                .toString();
    }


}

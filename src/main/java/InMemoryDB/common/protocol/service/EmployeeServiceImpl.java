package InMemoryDB.common.protocol.service;

import InMemoryDB.client.model.Employee;

import javax.json.Json;
import javax.json.JsonObject;

public class EmployeeServiceImpl implements ProtocolService {

    String operation;
    Employee employee;

    EmployeeServiceImpl(String operation, Employee employee) {
        this.operation = operation;
        this.employee = employee;
    }

    static JsonObject employeeObject(JsonObject object) {
        return object.getJsonObject("Employee");
    }

    @Override
    public String buildJsonRequest() {
        return Json.createObjectBuilder()
                .add("OperationTypes", operation)
                .add("Employee", Json.createObjectBuilder()
                        .add("Id", employee.getId())
                        .add("name", employee.getName())
                        .add("salary", employee.getSalary()))
                .build()
                .toString();

    }

    @Override
    public void ParseResponse(String json) {

    }
}

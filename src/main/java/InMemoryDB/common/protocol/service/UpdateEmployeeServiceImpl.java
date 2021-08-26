package InMemoryDB.common.protocol.service;

import InMemoryDB.client.model.Employee;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.SERVER_NOT_RESPONDING;

public class UpdateEmployeeServiceImpl extends EmployeeServiceImpl {
    public UpdateEmployeeServiceImpl(String operation, Employee employee) {
        super(operation, employee);
    }

    private static JsonObject getJsonObject(String json, String s) {
        json = json.replaceAll(s, "");
        JsonReader reader = Json.createReader(new StringReader(json));
        return reader.readObject();
    }

    @Override
    public void ParseResponse(String json) {
        String response;
        if (!json.equals("")) {
            JsonObject object = getJsonObject(json, "\r?\n");


            try {
                response = "Updated employee: " + employeeObject(object).getJsonNumber("Id") + " " + employeeObject(object).getString("name").trim() + " " + employeeObject(object).getJsonNumber("salary");
                display(response);
            } catch (Exception exception) {
                response = "no such id";
                display(response + " >> " + exception.getMessage());
            }


        } else {
            display(SERVER_NOT_RESPONDING);
        }
    }
}

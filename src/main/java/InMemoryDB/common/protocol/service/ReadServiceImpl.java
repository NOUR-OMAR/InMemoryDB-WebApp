package InMemoryDB.common.protocol.service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.SERVER_NOT_RESPONDING;

public class ReadServiceImpl extends IdServiceImpl {

    public ReadServiceImpl(String operation, String id) {
        super(operation, id);
    }

    private static JsonObject getJsonObject(String json) {
        json = json.replaceAll("\r?\n", "");
        JsonReader reader = Json.createReader(new StringReader(json));
        return reader.readObject();
    }

    @Override
    public void ParseResponse(String json) {
        String response;
        if (!json.equals("")) {
            JsonObject object = getJsonObject(json);
            JsonObject employeeObject = object.getJsonObject("Employee");
            try {
                response = "Fetched employee: " + employeeObject.getJsonNumber("Id") + " " + employeeObject.getString("name").trim() + " " + employeeObject.getJsonNumber("salary");
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

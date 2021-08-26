package InMemoryDB.common.protocol.service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.SERVER_NOT_RESPONDING;

public class NameServiceImpl implements ProtocolService {
    String operation;
    String name;

    public NameServiceImpl(String operation, String name) {
        this.operation = operation;
        this.name = name;

    }

    private static JsonObject getJsonObject(String json, String s) {
        json = json.replaceAll(s, "");
        JsonReader reader = Json.createReader(new StringReader(json));
        return reader.readObject();
    }

    @Override
    public String buildJsonRequest() {

        return Json.createObjectBuilder()
                .add("OperationTypes", operation)
                .add("name", name)
                .build()
                .toString();

    }

    @Override
    public void ParseResponse(String json) {
        if (!json.equals("")) {
            JsonObject object = getJsonObject(json, "\r?\n");
            String body = object.getString("body").replace("(", ":").replace("),", "\n").replaceAll("[\\[\\])]", "");


            display("Fetched employees with name " + name + " are : \n" + body);

        } else {
            display(SERVER_NOT_RESPONDING);
        }

    }
}

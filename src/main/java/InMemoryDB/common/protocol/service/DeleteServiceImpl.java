package InMemoryDB.common.protocol.service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.SERVER_NOT_RESPONDING;

public class DeleteServiceImpl extends IdServiceImpl {
    public DeleteServiceImpl(String operation, String id) {
        super(operation, id);
    }

    private static JsonObject getJsonObject(String json) {
        json = json.replaceAll("\r?\n", "");
        JsonReader reader = Json.createReader(new StringReader(json));
        return reader.readObject();
    }

    @Override
    public void ParseResponse(String json) {

        if (!json.equals("")) {
            JsonObject object = getJsonObject(json);
            String body = object.getString("body").replace("(", ":").replace("),", "\n").replaceAll("[\\[\\])]", "");
            display("Deleted employee is : \n" + body);

        } else {
            display(SERVER_NOT_RESPONDING);
        }

    }
}

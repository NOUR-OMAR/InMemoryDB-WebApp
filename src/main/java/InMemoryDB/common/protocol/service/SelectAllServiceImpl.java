package InMemoryDB.common.protocol.service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.SERVER_NOT_RESPONDING;

public class SelectAllServiceImpl extends ProtocolServiceImpl {


    public SelectAllServiceImpl(String request) {
        super(request);
    }

    private static JsonObject getJsonObject(String json, String s) {
        json = json.replaceAll(s, "");
        JsonReader reader = Json.createReader(new StringReader(json));
        return reader.readObject();
    }

    @Override
    public void ParseResponse(String json) {
        if (!json.equals("")) {
            JsonObject object = getJsonObject(json, "[;\n\r]");
            String body = object.getString("body").replace("(", ":").replace("),", "\n").replaceAll("[\\[\\])]", "");


            display("Fetched employees are : \n" + body);


        } else {
            display(SERVER_NOT_RESPONDING);
        }
    }
}

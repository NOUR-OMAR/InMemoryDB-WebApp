package InMemoryDB.common.protocol.service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.SERVER_NOT_RESPONDING;

public class SalaryServiceImpl implements ProtocolService {

    String request;
    String relation;
    String salary;

    public SalaryServiceImpl(String request, String relation, String salary) {
        this.request = request;
        this.relation = relation;
        this.salary = salary;
    }

    private static JsonObject getJsonObject(String json, String s) {
        json = json.replaceAll(s, "");
        JsonReader reader = Json.createReader(new StringReader(json));
        return reader.readObject();
    }

    @Override
    public String buildJsonRequest() {
        return Json.createObjectBuilder()
                .add("OperationTypes", request)
                .add("relation", relation)
                .add("salary", Integer.parseInt(salary))
                .build()
                .toString();

    }

    @Override
    public void ParseResponse(String json) {
        if (!json.equals("")) {
            javax.json.JsonObject object = getJsonObject(json, "\r?\n");
            String body = object.getString("body").replace("(", ":").replace("),", "\n").replaceAll("[\\[\\])]", "");
            display("Fetched employees with salary " + relation + " " + salary + " are : \n" + body);

        } else {
            display(SERVER_NOT_RESPONDING);
        }
    }
}

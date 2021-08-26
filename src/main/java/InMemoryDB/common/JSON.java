package InMemoryDB.common;

import lombok.Data;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

@Data
public class JSON implements JSONBuilder {

    public String[] buildEmployee(JsonObject jsonObject) {
        JsonObject employeeObject = jsonObject.getJsonObject("Employee");
        String id = employeeObject.getJsonNumber("Id").toString().trim();
        String name = employeeObject.getJsonString("name").getString().trim().replace("\"", "");
        String salary = employeeObject.getJsonNumber("salary").toString().trim();
        return new String[]{id, name, salary};
    }

    public String[] buildSalary(JsonObject jsonObject) {
        String relation = jsonObject.getJsonString("relation").toString().trim().replace("\"", "");
        String salary = jsonObject.getJsonNumber("salary").toString().trim();
        return new String[]{relation, salary};
    }

    public String[] buildID(JsonObject jsonObject) {
        String id = jsonObject.getJsonNumber("Id").toString().trim();

        return new String[]{id};
    }

    public String[] buildName(JsonObject jsonObject) {
        String name = jsonObject.getJsonString("name").getString().trim().replace("\"", "");

        return new String[]{name};
    }

    public JsonObject getJsonObject(String json) {
        JsonReader jsonReader = Json.createReader(new StringReader(json));

        return jsonReader.readObject();

    }


}

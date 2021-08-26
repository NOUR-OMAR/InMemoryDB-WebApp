package InMemoryDB.common;

import javax.json.JsonObject;

public interface JSONBuilder {


    String[] buildEmployee(JsonObject jsonObject);

    String[] buildSalary(JsonObject jsonObject);

    String[] buildID(JsonObject jsonObject);

    String[] buildName(JsonObject jsonObject);

    JsonObject getJsonObject(String json);
}

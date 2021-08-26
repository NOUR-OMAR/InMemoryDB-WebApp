package InMemoryDB.common.protocol.service;

import javax.json.Json;

public class IdServiceImpl implements ProtocolService {

    String operation;
    String id;

    public IdServiceImpl(String operation, String id) {
        this.operation = operation;
        this.id = id;
    }


    @Override
    public String buildJsonRequest() {
        return Json.createObjectBuilder()
                .add("OperationTypes", operation)
                .add("Id", Integer.parseInt(id))
                .build()
                .toString();
    }

    @Override
    public void ParseResponse(String json) {

    }
}

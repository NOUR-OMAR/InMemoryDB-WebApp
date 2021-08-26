package InMemoryDB.common.protocol.service;

import javax.json.Json;

public class ProtocolServiceImpl implements ProtocolService {

    String request;

    ProtocolServiceImpl(String request) {
        this.request = request;
    }

    @Override
    public String buildJsonRequest() {
        return Json.createObjectBuilder()
                .add("OperationTypes", request)
                .build()
                .toString();
    }

    @Override
    public void ParseResponse(String json) {

    }
}

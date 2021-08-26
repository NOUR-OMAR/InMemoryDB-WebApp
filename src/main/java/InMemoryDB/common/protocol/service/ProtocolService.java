package InMemoryDB.common.protocol.service;

public interface ProtocolService {

    String buildJsonRequest();

    void ParseResponse(String json);
}

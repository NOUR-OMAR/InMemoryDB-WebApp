package InMemoryDB.common.protocol.consumer;

import InMemoryDB.common.protocol.SocketConnection;
import InMemoryDB.common.protocol.service.ProtocolService;

public record Protocol(ProtocolService protocolService) implements Consumer {

    @Override
    public void process() {
        this.protocolService.ParseResponse(SocketConnection.SendRequestReceiveResponse(this.protocolService.buildJsonRequest()));

    }
}

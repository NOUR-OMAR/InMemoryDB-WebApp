package InMemoryDB.common.protocol.injector;

import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.ReadServiceImpl;

public class ReadServiceInjector implements ServiceInjector {
    String operation;
    String id;

    public ReadServiceInjector(String operation, String id) {
        this.operation = operation;
        this.id = id;
    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new ReadServiceImpl(operation, id));
    }
}

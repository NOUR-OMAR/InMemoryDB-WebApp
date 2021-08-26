package InMemoryDB.common.protocol.injector;

import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.CloseServiceImpl;

public class CloseServiceInjector implements ServiceInjector {
    String operation;

    public CloseServiceInjector(String operation) {
        this.operation = operation;

    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new CloseServiceImpl(operation));
    }
}

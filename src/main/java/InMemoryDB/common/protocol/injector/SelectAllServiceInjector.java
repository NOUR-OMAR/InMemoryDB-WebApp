package InMemoryDB.common.protocol.injector;

import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.SelectAllServiceImpl;

public class SelectAllServiceInjector implements ServiceInjector {
    String operation;

    public SelectAllServiceInjector(String operation) {
        this.operation = operation;

    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new SelectAllServiceImpl(operation));
    }
}

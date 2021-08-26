package InMemoryDB.common.protocol.injector;

import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.NameServiceImpl;

public class NameServiceInjector implements ServiceInjector {


    String operation;
    String name;

    public NameServiceInjector(String operation, String name) {
        this.operation = operation;
        this.name = name;

    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new NameServiceImpl(operation, name));
    }
}

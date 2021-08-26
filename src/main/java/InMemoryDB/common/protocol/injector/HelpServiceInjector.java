package InMemoryDB.common.protocol.injector;

import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.HelpServiceImpl;

public class HelpServiceInjector implements ServiceInjector {
    String operation;

    public HelpServiceInjector(String operation) {
        this.operation = operation;

    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new HelpServiceImpl(operation));
    }
}

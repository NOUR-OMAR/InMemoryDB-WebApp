package InMemoryDB.common.protocol.injector;

import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.DeleteServiceImpl;

public class DeleteServiceInjector implements ServiceInjector {

    String operation;
    String id;

    public DeleteServiceInjector(String operation, String id) {
        this.operation = operation;
        this.id = id;
    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new DeleteServiceImpl(operation, id));
    }
}

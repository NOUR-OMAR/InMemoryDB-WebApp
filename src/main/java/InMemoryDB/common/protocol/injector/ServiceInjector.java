package InMemoryDB.common.protocol.injector;

import InMemoryDB.common.protocol.consumer.Consumer;

public interface ServiceInjector {
    Consumer getConsumer();
}

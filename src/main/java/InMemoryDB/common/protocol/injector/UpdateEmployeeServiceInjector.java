package InMemoryDB.common.protocol.injector;

import InMemoryDB.client.model.Employee;
import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.UpdateEmployeeServiceImpl;

public class UpdateEmployeeServiceInjector implements ServiceInjector {
    String operation;
    Employee employee;

    public UpdateEmployeeServiceInjector(String operation, Employee employee) {
        this.employee = employee;
        this.operation = operation;

    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new UpdateEmployeeServiceImpl(operation, employee));
    }
}

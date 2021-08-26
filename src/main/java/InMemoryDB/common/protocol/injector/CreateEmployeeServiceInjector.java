package InMemoryDB.common.protocol.injector;

import InMemoryDB.client.model.Employee;
import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.CreateEmployeeServiceImpl;

public class CreateEmployeeServiceInjector implements ServiceInjector {
    String operation;
    Employee employee;

    public CreateEmployeeServiceInjector(String operation, Employee employee) {
        this.employee = employee;
        this.operation = operation;

    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new CreateEmployeeServiceImpl(operation, employee));
    }
}

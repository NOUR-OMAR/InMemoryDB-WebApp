package InMemoryDB.common.protocol.injector;

import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.consumer.Protocol;
import InMemoryDB.common.protocol.service.SalaryServiceImpl;

public class SalaryServiceInjector implements ServiceInjector {

    String request;
    String relation;
    String salary;

    public SalaryServiceInjector(String request, String relation, String salary) {
        this.request = request;
        this.relation = relation;
        this.salary = salary;
    }

    @Override
    public Consumer getConsumer() {
        return new Protocol(new SalaryServiceImpl(request, relation, salary));
    }
}

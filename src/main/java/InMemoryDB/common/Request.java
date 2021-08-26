package InMemoryDB.common;

import InMemoryDB.client.model.Employee;
import InMemoryDB.common.protocol.SocketConnection;
import InMemoryDB.common.protocol.consumer.Consumer;
import InMemoryDB.common.protocol.injector.*;
import lombok.Data;
import org.json.JSONException;

import static InMemoryDB.common.operation.OperationTypes.*;

@Data
public class Request {
    public void help() {
        ServiceInjector injector = new HelpServiceInjector(Help.toString());
        new ProcessInjector(injector).invoke();
    }

    public void selectAll() throws JSONException {
        ServiceInjector injector = new SelectAllServiceInjector(SelectAll.toString());
        new ProcessInjector(injector).invoke();
    }


    public void filterByName(String name) {

        ServiceInjector injector = new NameServiceInjector(FilterName.toString(), name);
        new ProcessInjector(injector).invoke();
    }


    public void createEmployee(String id, String name, String salary) {
        Employee employee = new Employee(id, name, salary);
        ServiceInjector injector = new CreateEmployeeServiceInjector(Create.toString(), employee);
        new ProcessInjector(injector).invoke();

    }

    public void updateEmployee(String id, String name, String salary) {
        Employee employee = new Employee(id, name, salary);
        ServiceInjector injector = new UpdateEmployeeServiceInjector(Update.toString(), employee);
        new ProcessInjector(injector).invoke();

    }


    public void deleteEmployee(String id) {
        ServiceInjector injector = new DeleteServiceInjector(Delete.toString(), id);
        new ProcessInjector(injector).invoke();

    }

    public void readEmployee(String id) {
        ServiceInjector injector = new ReadServiceInjector(Read.toString(), id);
        new ProcessInjector(injector).invoke();

    }

    public void filterSalary(String relation, String salary) {
        ServiceInjector injector = new SalaryServiceInjector(FilterSalary.toString(), relation, salary);
        new ProcessInjector(injector).invoke();
    }

    public void Close() throws JSONException {
        SocketConnection.setClose(true);
        ServiceInjector injector = new CloseServiceInjector(Close.toString());
        new ProcessInjector(injector).invoke();
    }


    private class ProcessInjector {
        private final ServiceInjector injector;

        public ProcessInjector(ServiceInjector injector) {
            this.injector = injector;
        }

        public void invoke() {
            Consumer app = injector.getConsumer();
            app.process();
        }
    }
}

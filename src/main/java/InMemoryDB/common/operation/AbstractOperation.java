package InMemoryDB.common.operation;


import InMemoryDB.server.database.employee_table.EmployeeTableDAO;

import static InMemoryDB.utils.Constant.DELAY_TIME;
import static InMemoryDB.utils.Constant.Display.display;

public abstract class AbstractOperation extends Thread implements Operation {
    protected String[] parameters;
    protected EmployeeTableDAO employeeTableDAO;

    //to avoid deadlock
    protected void doDelay() {
        display("\nRunning thread" + Thread.currentThread().getId());
        display("" + DELAY_TIME + " seconds delay...");
        try {
            sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void run();

}

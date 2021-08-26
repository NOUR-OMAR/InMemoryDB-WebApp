package InMemoryDB.common.menu;

import InMemoryDB.common.RequestBuilder;

import java.util.Scanner;

import static InMemoryDB.utils.Constant.Display.display;

public class EmployeeMenu implements Menu {
    RequestBuilder request = new RequestBuilder();

    @Override
    public void displayMenu() {
        boolean exit = false;
        int option;
        String id;
        String name;
        String salary;

        while (!exit) {

            display("\n0 : Exit");
            display("1 : Read employee");
            display("2 : Read Employees by name");
            display("3 : Filter salary");
            display("4 : Select All");
            display("5 : Help");
            display("6 : Close");
            Scanner scanner = new Scanner(System.in);
            try {
                display("Choose: ");
                option = scanner.nextInt();
                switch (option) {
                    case 0 -> {
                        display("Disconnecting");
                        request.createRequest().Close();
                        exit = true;
                    }
                    case 1 -> {
                        System.out.print("Enter id: ");
                        id = scanner.next();
                        request.createRequest().readEmployee(id);
                    }
                    case 2 -> {
                        System.out.print("Enter name: ");
                        name = scanner.next();
                        request.createRequest().filterByName(name);
                    }
                    case 3 -> {
                        System.out.print("Enter relation: ");
                        String relation = scanner.next();
                        System.out.print("Enter salary: ");
                        salary = scanner.next();

                        request.createRequest().filterSalary(relation, salary);
                    }
                    case 4 -> request.createRequest().selectAll();
                    case 5 -> request.createRequest().help();
                    case 6 -> {
                        request.createRequest().Close();
                        exit = true;
                    }
                    default -> display("Not An Option");
                }
            } catch (Exception e) {
                display("Not An Option");
                scanner.next();
            }
        }


    }

}

package InMemoryDB.common.menu;

import InMemoryDB.common.RequestBuilder;

import java.util.Scanner;

import static InMemoryDB.utils.Constant.Display.display;

public class AdminMenu implements Menu {

    @Override
    public void displayMenu() {

        RequestBuilder request = new RequestBuilder();

        boolean exit = false;
        int option;
        String id;
        String name;
        String salary;

        while (!exit) {


            display("\n0 :Exit");
            display("1 : Read employee");
            display("2 : Delete employee");
            display("3 : Create employee");
            display("4 : Update employee");
            display("5 : Read employees by name");
            display("6 : Filter to getEmployee specific salary");
            display("7 : Select All");
            display("8 : Help");
            display("9 :Close and save");
            Scanner scanner = new Scanner(System.in);
            try {
                display("Choose: ");
                option = scanner.nextInt();
                switch (option) {
                    case 0 -> {
                        System.out.println("Disconnecting");
                        request.createRequest().Close();
                        exit = true;
                    }
                    case 1 -> {
                        System.out.print("Enter id: ");
                        id = scanner.next();
                        request.createRequest().readEmployee(id);

                    }
                    case 2 -> {
                        System.out.print("Enter id: ");
                        id = scanner.next();
                        request.createRequest().deleteEmployee(id);
                    }
                    case 3 -> {
                        System.out.print("Enter id: ");
                        id = scanner.next();
                        System.out.print("Enter name: ");
                        name = scanner.next();
                        System.out.print("Enter salary: ");
                        salary = scanner.next();
                        request.createRequest().createEmployee(id, name, salary);
                    }
                    case 4 -> {
                        System.out.print("Enter id: ");
                        id = scanner.next();
                        System.out.print("Enter name: ");
                        name = scanner.next();
                        System.out.print("Enter salary: ");
                        salary = scanner.next();

                        request.createRequest().updateEmployee(id, name, salary);
                    }

                    case 5 -> {
                        System.out.print("Enter name: ");
                        name = scanner.next();
                        request.createRequest().filterByName(name);
                    }
                    case 6 -> {
                        System.out.print("Enter relation: ");
                        String relation = scanner.next();
                        System.out.print("Enter salary: ");
                        salary = scanner.next();


                        request.createRequest().filterSalary(relation, salary);

                    }
                    case 7 -> request.createRequest().selectAll();
                    case 8 -> request.createRequest().help();
                    case 9 -> {
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


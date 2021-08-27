package InMemoryDB.client;


import InMemoryDB.client.model.Admin;
import InMemoryDB.client.model.Employee;
import InMemoryDB.client.model.User;
import InMemoryDB.common.menu.AdminMenu;
import InMemoryDB.common.menu.EmployeeMenu;
import InMemoryDB.server.database.storage.log.TransactionLog;
import InMemoryDB.server.database.storage.log.TransactionLogger;
import InMemoryDB.server.database.user_database.UsersDatabase;
import lombok.Data;

import java.io.IOException;
import java.util.Scanner;

import static InMemoryDB.utils.Constant.Display.display;

@Data
class MainClient {
    static TransactionLogger transactionLog;

    static {
        try {
            transactionLog = new TransactionLog();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            start();
        } catch (Exception exception) {
            transactionLog.writeToCSV();

            exception.printStackTrace();
        }


    }


    private static void start() throws IOException {
        UsersDatabase userTable = UsersDatabase.getUsersDatabase();
        String userName, password;
        Scanner scanner = new Scanner(System.in);
        display("Enter the username: ");
        userName = scanner.nextLine();
        display("Enter the password: ");
        password = scanner.nextLine();
        Client client = new ClientBuilder().setUsername(userName).createClient();
        if (new UserChecker(userTable, userName, password, scanner, client).invoke()) return;
        scanner.close();
        client.disconnect();

    }

    private static boolean isUser(UsersDatabase userTable, String userName, String password) {
        return UsersDatabase.getAllUsers().containsKey(userName) && userTable.get(userName).getPassword().equals(password);
    }


    private static record UserInfo(String userName, String password) {

        public void invokeForEmployee() {
            User employee = new Employee(userName, password);
            employee.display(new EmployeeMenu());
        }

        public void invokeForAdmin() {
            User employee = new Admin(userName, password);
            employee.display(new AdminMenu());
        }


    }

    private static class UserChecker {
        private final UsersDatabase userTable;
        private final String userName;
        private final String password;
        private final Scanner scanner;
        private final Client client;
        private boolean myResult;

        public UserChecker(UsersDatabase userTable, String userName, String password, Scanner scanner, Client client) {
            this.userTable = userTable;
            this.userName = userName;
            this.password = password;
            this.scanner = scanner;
            this.client = client;
        }

        private static boolean isAdmin(UsersDatabase userTable, String password) {
            return isUser(userTable, "admin123", password);
        }

        private static boolean isClientStarted(Client client) {
            return client.start();
        }

        boolean is() {
            return myResult;
        }

        public boolean invoke() {
            if (isAdmin(userTable, password)) {
                if (!isClientStarted(client)) {
                    return true;
                } else {
                    new UserInfo(userName, password).invokeForAdmin();

                }

            } else if (isUser(userTable, userName, password)) {
                if (!isClientStarted(client)) {
                    return true;
                } else {
                    new UserInfo(userName, password).invokeForEmployee();

                }
            } else {
                display("Not a user , want to register?");
                User user = User.registerUser(scanner);
                UsersDatabase.getUsersDatabase().put(user);
                UsersDatabase.getUsersDatabase().close();
                if (!isClientStarted(client)) return true;
                else {
                    new UserInfo(userName, password).invokeForEmployee();


                }
            }
            return false;
        }
    }
}
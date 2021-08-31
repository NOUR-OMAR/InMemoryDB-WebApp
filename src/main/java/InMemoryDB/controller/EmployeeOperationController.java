package InMemoryDB.controller;

import InMemoryDB.database.Database;
import InMemoryDB.database.departments_table.DepartmentsTableDAO;
import InMemoryDB.database.employee_table.EmployeeTableDAO;
import InMemoryDB.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class EmployeeOperationController {

    @Autowired
    EmployeeTableDAO employeeTableDAO;
    @Autowired
    DepartmentsTableDAO departmentTableDAO;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "LoginView";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showPage(@RequestParam String username,  ModelMap modelMap) throws IOException {

      // String username= String.valueOf(modelMap.getAttribute("username"));
        modelMap.addAttribute("username", username);
        int userId = Database.getDatabase().getUser(username).getId();
        if (Database.getDatabase().getUser(username).getRole().equals("ADMIN"))
            return showAdminView(modelMap);
        else if (Database.getDatabase().getUser(username).getRole().equals("EMPLOYEE"))
            return showEmployeeInfo(userId, modelMap);

    return showLoginPage();}


    @RequestMapping(value = "/adminView")
    public String showAdminView(ModelMap modelMap) {

        List<Employee> employees = employeeTableDAO.selectAll();
        modelMap.addAttribute("employees", employees);
        return "adminView";

    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public String addEmployee(@RequestParam int id, @RequestParam String name, @RequestParam int salary, @RequestParam int departmentId, ModelMap modelMap) throws IOException {

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("salary", salary);
        modelMap.addAttribute("departmentId", departmentId);
        Employee employee = new Employee(id, name, salary, departmentId);
        employeeTableDAO.createEmployee(employee);

        return "redirect:/adminView";

    }

    @RequestMapping(value = "/updateEmployee", method = RequestMethod.GET)
    public String updateEmployee(@RequestParam int id, @RequestParam String name, @RequestParam int salary, @RequestParam int departmentId, ModelMap modelMap) throws IOException {

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("salary", salary);
        modelMap.addAttribute("departmentId", departmentId);
        Employee employee = new Employee(id, name, salary, departmentId);
        employeeTableDAO.updateEmployee(employee);

        return "redirect:/adminView";
    }


    @RequestMapping(value = "/deleteEmployee-{id}", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable int id) throws IOException {
        employeeTableDAO.deleteEmployee(id);

        return "redirect:/adminView";
    }

    @RequestMapping(value = "/filterSalaryEQ", method = RequestMethod.GET)
    public String filterSalaryEQ(@RequestParam int salary, ModelMap modelMap) throws IOException {

        List<Employee> employees = new ArrayList<>();
        employees.addAll(employeeTableDAO.filterBySalaryEQ(salary).values());
        modelMap.addAttribute("employees", employees);

        return "ListView";
    }

    @RequestMapping(value = "/filterSalaryLT", method = RequestMethod.GET)
    public String filterSalaryLT(@RequestParam int salary, ModelMap modelMap) throws IOException {

        List<Employee> employees = new ArrayList<>();
        employees.addAll(employeeTableDAO.filterBySalaryLT(salary).values());
        modelMap.addAttribute("employees", employees);

        return "ListView";
    }

    @RequestMapping(value = "/filterSalaryGT", method = RequestMethod.GET)
    public String filterSalaryGT(@RequestParam int salary, ModelMap modelMap) throws IOException {

        List<Employee> employees = new ArrayList<>();
        employees.addAll(employeeTableDAO.filterBySalaryGT(salary).values());
        modelMap.addAttribute("employees", employees);

        return "ListView";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String close() throws IOException {
        employeeTableDAO.close();
        return "logout";
    }

    @RequestMapping(value = "/listNames", method = RequestMethod.GET)
    public String showNames(@RequestParam String name, ModelMap modelMap) throws IOException {
        List<Employee> employees = new ArrayList<>();
        employees.addAll(employeeTableDAO.filterByName(name).values());
        modelMap.addAttribute("employees", employees);

        return "ListView";
    }

    @RequestMapping(value = "/employeeView", method = RequestMethod.GET)
    public String showEmployeeInfo(@RequestParam int id, ModelMap modelMap) throws IOException {
        Employee employee = employeeTableDAO.readEmployee(id);
        modelMap.addAttribute("employee", employee);

        return "employeeView";
    }


}

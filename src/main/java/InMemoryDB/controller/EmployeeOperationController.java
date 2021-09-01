package InMemoryDB.controller;

import InMemoryDB.database.Database;
import InMemoryDB.database.departments_table.DepartmentsTableDAO;
import InMemoryDB.database.employee_table.EmployeeTableDAO;
import InMemoryDB.model.Department;
import InMemoryDB.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes("username")
public class EmployeeOperationController {

    @Autowired
    EmployeeTableDAO employeeTableDAO;
    @Autowired
    DepartmentsTableDAO departmentTableDAO;


    @GetMapping(value = "/login")
    public String showLoginPage( ) {
        return "LoginView";
    }

    @Resource(name="authenticationManager")
    private AuthenticationManager authManager;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestParam("username") final String username, @RequestParam("password") final String password, final HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
    }



    @GetMapping(value = "/adminView")

    public String showAdminView(ModelMap modelMap) {

        List<Employee> employees = employeeTableDAO.selectAll();
        modelMap.addAttribute("employees", employees);

        List<Department> departments = departmentTableDAO.selectAll();
        modelMap.addAttribute("departments", departments);
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

    @GetMapping(value = "/employeeView")
    public String showEmployeeInfo(final HttpServletRequest request, ModelMap modelMap) throws IOException {
        HttpSession session = request.getSession(true);
        String username= String.valueOf(session.getAttribute("username"));
        Employee employee = employeeTableDAO.readEmployee(Database.getDatabase().getUser(username).getId());
        modelMap.addAttribute("employee", employee);

        return "employeeView";
    }


}

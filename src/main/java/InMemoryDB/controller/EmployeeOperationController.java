package InMemoryDB.controller;

import InMemoryDB.database.departments_table.DepartmentsTableDAO;
import InMemoryDB.database.employee_table.EmployeeTableDAO;
import InMemoryDB.database.users_table.UserTableDAO;
import InMemoryDB.model.Department;
import InMemoryDB.model.Employee;
import InMemoryDB.model.User;
import InMemoryDB.security.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    @Autowired
    UserTableDAO userTableDAO;
    @Autowired
    UserRoles userRoles;


    @Resource(name = "authenticationManager")
    private AuthenticationManager authManager;


    @GetMapping(value = "/login")
    public ModelAndView showLoginPage(String error, String logout) {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null)
            modelAndView.addObject("errorMessage", "Bad Credentials!");

        if (logout != null)
            modelAndView.addObject("message", "You have been logged out successfully.");

        modelAndView.setViewName("LoginView");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        ModelAndView modelAndView = new ModelAndView();


        modelAndView.addObject("username", username);
        modelAndView.addObject("password", password);
        modelAndView.setViewName("/login");

       /* System.out.println("nn");

        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(username, password);

        HttpSession session = request.getSession(true);
        boolean error = (boolean) session.getAttribute("error");

        Authentication auth = authManager.authenticate(authReq);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        System.out.println(auth);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        if (!securityContext.getAuthentication().isAuthenticated()) {
            System.out.println("nn");
            session.setAttribute("errorMessage", "bad credential");
        }*/

        return modelAndView;
    }


    @GetMapping(value = "/adminView")
    public ModelAndView showAdminView() throws IOException {

        ModelAndView modelAndView = new ModelAndView();

        List<Employee> employees = employeeTableDAO.selectAll();
        modelAndView.addObject("employees", employees);

        List<Department> departments = departmentTableDAO.selectAll();
        modelAndView.addObject("departments", departments);
        modelAndView.setViewName("adminView");

        return modelAndView;

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("id") final int id,
                           @RequestParam("username") final String username,
                           @RequestParam("password") final String password
                          ) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        User user = new User(id, username, password, "EMPLOYEE");
        userTableDAO.createUser(user);
        userRoles.roles.put("EMPLOYEE", new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), userRoles.getAuthority("ROLE_USER")));
        System.out.println(userTableDAO.selectAll());
        modelAndView.setViewName("redirect:/register");
        return modelAndView;

    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getRegister( ) throws IOException {
        ModelAndView modelAndView = new ModelAndView();


        modelAndView.addObject("message", "registered successfully ,please login again");
        modelAndView.setViewName("redirect:/login");
        return modelAndView;

    }




    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)//employee employee object
    public ModelAndView addEmployee(/*@RequestParam int id,
                              @RequestParam String name,
                              @RequestParam int salary,
                              @RequestParam int departmentId, ModelMap modelMap*/
            Employee newEmployee) throws IOException {


       /* modelMap.addAttribute("id", id);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("salary", salary);
        modelMap.addAttribute("departmentId", departmentId);
        Employee employee = new Employee(id, name, salary, departmentId);
        employeeTableDAO.createEmployee(employee);

        return "redirect:/adminView";*/

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newEmployee", newEmployee);
        modelAndView.setViewName("redirect:/adminView");
        employeeTableDAO.createEmployee(newEmployee);
        return modelAndView;


    }


    @RequestMapping(value = "/updateEmployee", method = RequestMethod.GET)
    public String updateEmployee(@RequestParam int id,
                                 @RequestParam String name,
                                 @RequestParam int salary,
                                 @RequestParam int departmentId,
                                 ModelMap modelMap) throws IOException {

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
    public ModelAndView filterSalaryEQ(@RequestParam int salary) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListView");
        List<Employee> employees = new ArrayList<>(employeeTableDAO.filterBySalaryEQ(salary).values());
        modelAndView.addObject("employees", employees);

        return modelAndView;
    }

    @RequestMapping(value = "/filterSalaryLT", method = RequestMethod.GET)
    public ModelAndView filterSalaryLT(@RequestParam int salary) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListView");
        List<Employee> employees = new ArrayList<>(employeeTableDAO.filterBySalaryLT(salary).values());
        modelAndView.addObject("employees", employees);

        return modelAndView;
    }

    @RequestMapping(value = "/filterSalaryGT", method = RequestMethod.GET)
    public ModelAndView filterSalaryGT(@RequestParam int salary) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListView");
        List<Employee> employees = new ArrayList<>(employeeTableDAO.filterBySalaryGT(salary).values());
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String close() throws IOException {
        employeeTableDAO.close();
        return "logout";
    }

    @RequestMapping(value = "/listNames", method = RequestMethod.GET)
    public ModelAndView showNames(@RequestParam String name) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListView");
        List<Employee> employees = new ArrayList<>(employeeTableDAO.filterByName(name).values());
        modelAndView.addObject("employees", employees);

        return modelAndView;
    }

    @GetMapping(value = "/employeeView")
    public String showEmployeeInfo(final HttpServletRequest request, ModelMap modelMap) throws IOException {
        HttpSession session = request.getSession(true);
        String username = String.valueOf(session.getAttribute("username"));
        Employee employee = employeeTableDAO.readEmployee(userTableDAO.readUser(username).getId());
        modelMap.addAttribute("employee", employee);

        return "employeeView";
    }


}

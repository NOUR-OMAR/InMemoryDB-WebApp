package InMemoryDB.controller;

import InMemoryDB.database.employee_table.EmployeeTableDAO;
import InMemoryDB.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes("username")
public class EmployeeOperationController {

    @Autowired
    EmployeeTableDAO employeeTableDAO;

    @GetMapping(value = "/addEmployee")//TODO  employee object
    public ModelAndView addEmployee(@RequestParam int id,
                              @RequestParam String name,
                              @RequestParam int salary,
                              @RequestParam int departmentId, ModelMap modelMap
          ) throws IOException {

        ModelAndView modelAndView = new ModelAndView();

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("salary", salary);
        modelMap.addAttribute("departmentId", departmentId);
        Employee employee = new Employee(id, name, salary, departmentId);
        employeeTableDAO.createEmployee(employee);

        modelAndView.setViewName("redirect:/adminView");
        return modelAndView;


    }


    @GetMapping(value = "/updateEmployee")
    public ModelAndView updateEmployee(@RequestParam int id,
                                 @RequestParam String name,
                                 @RequestParam int salary,
                                 @RequestParam int departmentId,
                                 ModelMap modelMap) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelMap.addAttribute("id", id);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("salary", salary);
        modelMap.addAttribute("departmentId", departmentId);
        Employee employee = new Employee(id, name, salary, departmentId);
        employeeTableDAO.updateEmployee(employee);

        modelAndView.setViewName("redirect:/adminView");
        return modelAndView;

    }


    @GetMapping(value = "/deleteEmployee-{id}")
    public ModelAndView deleteEmployee(@PathVariable int id) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/adminView");
        employeeTableDAO.deleteEmployee(id);

        return modelAndView;
    }

    @GetMapping(value = "/filterSalaryEQ")
    public ModelAndView filterSalaryEQ(@RequestParam int salary) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListView");
        List<Employee> employees = new ArrayList<>(employeeTableDAO.filterBySalaryEQ(salary).values());
        modelAndView.addObject("employees", employees);

        return modelAndView;
    }

    @GetMapping(value = "/filterSalaryLT")
    public ModelAndView filterSalaryLT(@RequestParam int salary) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListView");
        List<Employee> employees = new ArrayList<>(employeeTableDAO.filterBySalaryLT(salary).values());
        modelAndView.addObject("employees", employees);

        return modelAndView;
    }

    @GetMapping(value = "/filterSalaryGT")
    public ModelAndView filterSalaryGT(@RequestParam int salary) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListView");
        List<Employee> employees = new ArrayList<>(employeeTableDAO.filterBySalaryGT(salary).values());
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }


    @GetMapping(value = "/listNames")
    public ModelAndView showNames(@RequestParam String name) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListView");
        List<Employee> employees = new ArrayList<>(employeeTableDAO.filterByName(name).values());
        modelAndView.addObject("employees", employees);

        return modelAndView;
    }




}

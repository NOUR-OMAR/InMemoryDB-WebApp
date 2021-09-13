package InMemoryDB.controller;

import InMemoryDB.database.departments_table.DepartmentsTableDAO;
import InMemoryDB.database.employee_table.EmployeeTableDAO;
import InMemoryDB.model.Department;
import InMemoryDB.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {


    @Autowired
    EmployeeTableDAO employeeTableDAO;

    @Autowired
    DepartmentsTableDAO departmentTableDAO;

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

}

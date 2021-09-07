package InMemoryDB.controller;

import InMemoryDB.database.employee_table.EmployeeTableDAO;
import InMemoryDB.database.users_table.UserTableDAO;
import InMemoryDB.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class EmployeeViewController {
    @Autowired
    EmployeeTableDAO employeeTableDAO;

    @Autowired
    UserTableDAO userTableDAO;

    @GetMapping(value = "/employeeView")
    public ModelAndView showEmployeeInfo(final HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(true);
        String username = String.valueOf(session.getAttribute("username"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employeeView");
        Employee employee = employeeTableDAO.readEmployee(userTableDAO.readUser(username).getId());
        modelAndView.addObject("employee", employee);

        return modelAndView;
    }
}

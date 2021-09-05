package InMemoryDB.controller;


import InMemoryDB.database.employee_table.EmployeeTableDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@SessionAttributes("username")
public class LoginController {

    @Autowired
    EmployeeTableDAO employeeTableDAO;
    @GetMapping(value = "/login")
    public ModelAndView showLoginPage(String error, String logout, String success) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null)
            modelAndView.addObject("errorMessage", "Bad Credentials!");

        if (logout != null)
        {  modelAndView.addObject("message", "You have been logged out successfully.");

          employeeTableDAO.close();

        }
        if (success != null)
            modelAndView.addObject("message", "registered successfully ,please login again");


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

        return modelAndView;
    }

}

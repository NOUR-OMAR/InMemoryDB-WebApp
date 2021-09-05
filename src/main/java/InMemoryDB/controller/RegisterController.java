package InMemoryDB.controller;

import InMemoryDB.database.users_table.UserTableDAO;
import InMemoryDB.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public class RegisterController {
    @Autowired
    UserTableDAO userTableDAO;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("id") final int id,
                                 @RequestParam("username") final String username,
                                 @RequestParam("password") final String password
    ) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        User user = new User(id, username, password, "EMPLOYEE");
        userTableDAO.createUser(user);
        userTableDAO.close();
        System.out.println(userTableDAO.selectAll());
        modelAndView.setViewName("redirect:/login?success");
        return modelAndView;

    }

}

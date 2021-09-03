package InMemoryDB.controller;

import InMemoryDB.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("Sec")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping("auth")
    public boolean authenticate(@RequestParam(name = "username", required = false, defaultValue = "") String username,
                                @RequestParam(name = "password", required = false, defaultValue = "") String password) throws IOException {

        return securityService.authenticate(username,password);

    }

}
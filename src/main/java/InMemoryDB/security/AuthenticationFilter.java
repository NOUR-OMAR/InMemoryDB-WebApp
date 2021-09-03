package InMemoryDB.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean authenticated = securityService.authenticate(username,password);

        if (authenticated) {
            chain.doFilter(request, response);
        }

    }
}

# InMemoryDB-WebApp

# Authorization & Authentication:
## Authorization:
### I used Spring Security to redirect to different types of pages after Login:
1- The Spring Security Configuration
Spring Security provides a component that has the direct responsibility of deciding what to do after a successful *authentication* – 
_the AuthenticationSuccessHandler_.
 
#### Firstly, users and their roles needs to be configured.
In my code I implemented UserDetailService with 2 types of users, each having one single role.
I have an admin , and employees 
- admin has the role of ADMIN,
- and every employee has the role of USER

as we can see in the code below:
I give the admin the ROLE_ADMIN,
and the employees with role EMPLOYEE in the users table will have the ROLE_USER
```Java
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
	UserTableDAO userTableDAO;
  private Map<String, User> roles = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        roles.put("ADMIN", new User(userTableDAO.readUser("admin123").getUsername(), userTableDAO.readUser("admin123").getPassword(), getAuthority("ROLE_ADMIN")));
        for (InMemoryDB.model.User user : userTableDAO.selectAll()) {
            if (user.getRole().equals("EMPLOYEE"))
                roles.put("EMPLOYEE", new User(user.getUsername(), user.getPassword(), getAuthority("ROLE_USER")));
        }

    }
    .
    .
    .

```

### So After a successful login, each will be redirected to their page:

this custom SuccessHandler is defined as a bean :

```Java

 @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
        return new UrlAuthenticationSuccessHandler();
    }
    
```    

and put it in the ``` successHandler ``` method that accepts it to redirect every user to their custom page.

I configure the basic @Configuration ``` SecurityConfigurer ``` class that extends ``` WebSecurityConfigurerAdapter ``` , where the ``` getAuthenticationSuccessHandler ```  is called inside ``` successHandler ``` and the ``` configure ``` method is overriden to configure the redirection of users based on their roles:

```Java
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    public SecurityConfigurer() {
        super();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login","/register").permitAll()
                .antMatchers("/").hasAnyRole("ADMIN")
                .antMatchers("/employee").hasAnyRole("USER","ADMIN")
                .and()
                .formLogin().loginPage("/login")
                .successHandler(getAuthenticationSuccessHandler())
                .and().logout().permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
..
..
..
}
```
So here I give the Admin the access to every page ,and the user to only the employee page .
 -------------
 #### 3. The Custom Authentication Success Handler
Besides the AuthenticationSuccessHandler interface, Spring also provides a sensible default for this strategy component – the ``` AbstractAuthenticationTargetUrlRequestHandler ``` and a simple implementation – the ``` UrlAuthenticationSuccessHandler ```.so this implementations will determine the URL after login and perform a redirect to that URL.

So after the implementation of the interface ``` AuthenticationSuccessHandler ``` ,i needed to provide a custom implementation of the success handler.
This implementation is going to determine the URL to redirect the user to after login based on the role of the user. 

First of all, we need to override the ``` onAuthenticationSuccess ``` method:
 ```Java  
 public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public UrlAuthenticationSuccessHandler() {
        super();
    }

    

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
    .
    .
    .
```
In the implementation of ``` onAuthenticationSuccess ``` we call these methods:

``` Java 
  protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, IOException {
        final String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
    
    
     protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
```


``` clearAuthenticationAttributes ``` : Removes temporary authentication-related data which may have been stored in the session during the authentication process.
     
and the actaul mapping for role to its target URL is done in this method:

``` Java 

protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_ADMIN", "/adminView");
        roleTargetUrlMap.put("ROLE_USER", "/employeeView");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }
```
as we can see admin will be redirected to "/adminView" url ,and user to "/employeeView" url.



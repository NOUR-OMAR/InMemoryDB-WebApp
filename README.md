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
- and every employee has the role of EMPLOYEE


So to get the users roles from users table in the database I Implemented UserDetails and UserDetaisService:
Spring Security requires an implementation of UserDetails interface to know about the authenticated user information, so I created the NewUserDetails class as follows:

```Java
public class NewUserDetails implements UserDetails {

    private User user;

    public NewUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      String role = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
        
        return authorities;    }

    @Override
    public String getPassword() {
        System.out.println(user.getPassword());

        return  new BCryptPasswordEncoder().encode(user.getPassword());

    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    ..
    ..
    
 ```
 ```Java
 public Collection<? extends GrantedAuthority> getAuthorities()
 ``` 
 method ,returns the roles(authorities) of the user from the users table to be used by Spring Security in the authorization process.
 after that we need to code an implementation of the UserDetailsService interface defined by Spring Security with the following code:
 
 ```Java
 @Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserTableDAO userTableDAO;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryDB.model.User user = null;
        try {
            user = userTableDAO.readUser(username);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new UsernameNotFoundException("Could not find User");
        }
        return new NewUserDetails(user);
    }

}
```
In this class , the method  ```public UserDetails loadUserByUsername(String username)``` will be invoked by Spring Security when authenticating the users. It will use the readUser(username) method that comes from UserTableDAO to get the user with its information using its username from the users table in the database ,and put redirect it to NewUserDetails to make use from that are loaded and get the authorities of the users.


## Configure Spring Security Authentication & Authorization :

### And to connect all the pieces together, Spring Security configuration class is implemented as follows :

I configure the basic @Configuration ``` SecurityConfigurer ``` class that extends ``` WebSecurityConfigurerAdapter ```  .
```Java
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    UserTableDAO userTableDAO;

    public SecurityConfigurer() {
        super();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        auth.authenticationProvider(authenticationProvider());


    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login**", "/register**").permitAll()
                .antMatchers("/").hasAuthority("ADMIN")
                .antMatchers("/employee").hasAnyAuthority("EMPLOYEE", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .successHandler(getAuthenticationSuccessHandler())
                .and().logout().permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return new UrlAuthenticationSuccessHandler();
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
```
these methods are needed to configure an authentication provider :

```Java 
@Bean
    public UserDetailsService userDetailsService() 
    
@Bean
    public BCryptPasswordEncoder passwordEncoder()
@Bean
    public DaoAuthenticationProvider authenticationProvider()
@Override
    protected void configure(AuthenticationManagerBuilder auth)
     
``` 

And in this method I configure HTTP Security for authentication and authorization then redirect the users based on their rolse . Where the ``` getAuthenticationSuccessHandler ```  is called inside ``` successHandler ``` .

 ```Java
 @Override
    protected void configure(HttpSecurity http)
```

### So After a successful login, each will be redirected to their page:

this custom SuccessHandler is defined as a bean :

```Java

 @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
        return new UrlAuthenticationSuccessHandler();
    }
    
```    

and it is put in the ``` successHandler ``` method that accepts it to redirect every user to their custom page.

I give the Admin the access to every page ,and the user to only the employee page .

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
        roleTargetUrlMap.put("ADMIN", "/adminView");
        roleTargetUrlMap.put("EMPLOYEE", "/employeeView");

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





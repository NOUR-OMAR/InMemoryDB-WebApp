# InMemoryDB-WebApp

# Effective Java Code Principles
 
## Effective Java Book : 
THIS book is designed to help you make effective use of the Java programming language and its fundamental libraries: java.lang, java.util, and java.io, and subpackages such as java.util.concurrent and java.util.function. Other libraries are discussed from time to time.

This book consists of ninety items, each of which conveys one rule. The rules capture practices generally held to be beneficial by the best and most experienced programmers. The items are loosely grouped into eleven chapters, each covering one broad aspect of software design. The book is not intended to be read from cover to cover: each item stands on its own, more or less. 


### Code principles:
#### Creating and Destroying Objects :
1. Item 3: Enforce the singleton property with a private constructor or an enum type:

   a. Defention: A singleton is simply a class that is instantiated exactly once [Gamma95]. Singletons typically represent either a stateless object such as a function (Item 24) or a system component that is intrinsically unique. Making a class a singleton can make it difficult to test its clients because it’s impossible to substitute a mock implementation for a singleton unless it implements an interface that serves as its type.


   b. Implementation:
There are two common ways to implement singletons. Both are based on keeping the constructor private and exporting a public static member to provide access to the sole instance. 
In the second approach to implementing singletons, the public member is a static factory method:

All calls to Database.getDatabse() return the same object reference, and no other Databas instance will ever be created.
```Java
public class Database {

 private Database() throws IOException {
       ...
    }

    public static synchronized Database getDatabase() throws IOException {
       ...
    }
    ....
    }
```
2. Item 5: Prefer dependency injection to hardwiring resources:
  a. Defention:Many classes depend on one or more underlying resources. When a class might have many variants, you should pass the resource into the
constructor when creating a new instance, this allows for an easier code scalability when new
requirements are needed in the project.
  b. Implementation : since I used spring boot framework ,Dependency Injection is a fundamental aspect of the Spring framework, through which the Spring container “injects” objects into other objects or “dependencies”. Simply put, this allows for loose coupling of components and moves the responsibility of managing components onto the container. 
  for example : 
  1- Constructor-Based Dependency Injection: In the case of constructor-based dependency injection, the container will invoke a constructor with arguments each representing a dependency we want to set. Spring resolves each argument primarily by type, followed by name of the attribute, and index for disambiguation. Let's see the configuration of a bean and its dependencies using annotations:
  
```Java 
@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

@Bean
    public UserDetailsService userDetailsService() {
      ..
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        ..
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
      ..
    }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
      ..
    }

}
  
```
The @Configuration annotation indicates that the class is a source of bean definitions. We can also add it to multiple configuration classes.
We use the @Bean annotation on a method to define a bean.  
 2- Field-Based Dependency Injection :
 In case of Field-Based DI, we can inject the dependencies by marking them with an @Autowired annotation:
```Java
@Controller
public class AdminController {


    @Autowired
    EmployeeTableDAO employeeTableDAO;
    @Autowired
    DepartmentsTableDAO departmentTableDAO;
...

}
```
 While constructing the AdminController object, if there's no constructor or setter method to inject the EmployeeTableDAO bean or DepartmentsTableDAO, the container will use reflection to inject them into AdminController.

3. Item 6: Avoid creating unnecessary objects
  a. Definition: It is often appropriate to reuse a single object instead of creating a new functionally
equivalent object each time it is needed. Reuse can be both faster and more stylish. An object can
always be reused if it is immutable.
  b. Implementation:In spring boot framwork Autowiring using @Autowired annotation creates the objects for the user when needed.
4. Item 8: Avoid finalizers and cleaners
  a. Finalizers are unpredictable, often dangerous, and generally unnecessary. Their use can cause erratic behavior, poor performance, and portability problems. 
  b. Implementation: I didn't use Finalizers in my project
5. Item 9: Prefer try-with-resources to try-finally :
  a. Definition: The Java libraries include many resources that must be closed manually by invoking a close method.o be usable with this construct, a resource must implement the AutoCloseable interface, which consists of a single void-returning close method. Many classes and interfaces in the Java libraries and in third-party libraries now implement or extend AutoCloseable. If you write a class that represents a resource that must be closed, your class should implement AutoCloseable too.
  
  
  b. Implementation : I used try with resourses in my code in multiple methods and for example in tryWritingToFile method as shown below :
  
  ```Java
  
   public void tryWritingToFile(StringBuilder stringBuilder, String filePath) throws IOException {
        File file = new ClassPathResource(filePath).getFile();

        try (FileWriter fileWriter = new FileWriter(file, false)) {
           ..
        } catch (IOException ignored) {
           ..
        }
    }

```
#### Methods Common to All Objects :

1. Item 12: Always override toString :
 a. Definition: Always provide programmatic access to all of the information contained in the value
returned by toString so the users of the object don't need to parse the output of the toString
 b. Implementation : Any class definition may be annotated with @ToString to let lombok generate an implementation of the toString() method. By default, it'll print your class name, along with each field, in order, separated by commas. for example in Employee class :
 
 ```Java
@ToString
@Data
public class Employee extends User {
...
}
```

### Classes and Interfaces :

1. Item 15: Minimize the accessibility of classes and members : 
  a. Definition : The single most important factor that distinguishes a well-designed component from a poorly designed one is the degree to which the component hides its internal data and other implementation details from other components. A well-designed component hides all its implementation details, cleanly separating its API from its implementation. Components then communicate only through their APIs and are oblivious to each others’ inner workings. This concept, known as information hiding or encapsulation, is a fundamental tenet of software design.
  b. Implementation :I set the fields to be private and annotated them with @Getter ,and @Setter annotations to encapsualtes them as shown below :
  
  ```Java
  
   @Getter
    private static final LRUCache<Integer, Object> tableLRUCache = new LRUCache<>(CACHE_MAX_SIZE);

    @Getter
    private static final LRUCache<String, User> usersLRUCache = new LRUCache<>(CACHE_MAX_SIZE);

    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Employee> allEmployees;

    @Setter
    @Getter
    private static ConcurrentHashMap<Integer, Department> allDepartments;

    @Setter
    @Getter
    private static ConcurrentHashMap<String, User> allUsers;
```

2. Item 16: In public classes, use accessor methods, not public fields :
  a.  Definition: Degenerate classes should not be public and should be replaced by accessor methods
(getters) and mutators (setters). 
   b. Implementation : as the previous point
3. Item 17: Minimize mutability :
  a. Definition : An immutable class is simply a class whose instances cannot be modified. All the
information contained in each instance is fixed for the lifetime of the object, so no changes can
ever be observed.
  b. Implementation : I didn't make immutable classes.
4. Item 20: Prefer interfaces to abstract classes :
  a. Definition: Because Java permits only single inheritance, this restriction on abstract classes
severely constrains their use as type definitions. Any class that defines all the required methods
and obeys the general contract is permitted to implement an interface, regardless of where the
class resides in the class hierarchy.
   b. Implementation : I didn't use abstarct classes , and I used alot of interface classes : 
   
   ![image](https://user-images.githubusercontent.com/77013882/132748879-cdf9411c-f130-4781-9f4c-3d23b8812a28.png)
 
 
 #### Generics :
 
1. Item 28: Prefer lists to arrays
  a. Definition:
Arrays differ from generic types in two important ways. First, arrays are covariant. This scary-sounding word means simply that if Sub is a subtype of Super, then the array type Sub[] is a subtype of the array type Super[]. Generics, by contrast, are invariant: for any two distinct types Type1 and Type2, List<Type1> is neither a subtype nor a supertype of List<Type2> [JLS, 4.10; Naftalin07, 2.5]. You might think this means that generics are deficient, but arguably it is arrays that are deficient. 
  b. Implementation: I didn't use arrays in the code , and I used Lists when needed :
 
 ```Java
  @Override
    public List<Employee> selectAll() {
        return new ArrayList<>(database.getEmployeesTable().values());
    }
```
 

---------------------------------------------------------------------------------------------------------------------------------------
# Authorization & Authentication:

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
In this class , the method  ```public UserDetails loadUserByUsername(String username)``` will be invoked by Spring Security when authenticating the users. It will use the readUser(username) method that comes from UserTableDAO to get the user with its information using its username from the users table in the database ,and redirect it to NewUserDetails to make user from the information that are loaded and get the authorities of the users.


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



## HandlerInterceptor:
One of the use cases of HandlerInterceptor is adding common/user specific parameters to a model, which will be available on each generated view.

In my app , I used custom interceptor implementation to add logged user's username to model parameters.

I extended HandlerInterceptorAdapter, as I only want to implement preHandle() and postHandle() methods.

``` Java
public class UserInterceptor extends HandlerInterceptorAdapter {
..
..
}
```



As I mentioned before, I want to add logged user's name to a model. First of all, I need to check if a user is logged in. We may obtain this information by checking SecurityContextHolder:

```Java
public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }
```
When an HttpSession is established, but nobody is logged in, a username in Spring Security context equals to anonymousUser

------

##### Method preHandle()
Before handling a request, we cannot access model parameters. In order to add username, we need to use HttpSession to set parameters:

```Java
@Override
public boolean preHandle(HttpServletRequest request,
  HttpServletResponse response, Object object) throws Exception {
    if (isUserLogged()) {
        addToModelUserDetails(request.getSession());
    }
    return true;
}
```
This is crucial if we are using some of this information before handling a request. As we see, here we are checking if a user is logged in and then add parameters to the request by obtaining its session:

```Java
private void addToModelUserDetails(HttpSession session) {
    log.info("=============== addToModelUserDetails =========================");
    
    String loggedUsername 
      = SecurityContextHolder.getContext().getAuthentication().getName();
    session.setAttribute("username", loggedUsername);
    
    log.info("user(" + loggedUsername + ") session : " + session);
    log.info("=============== addToModelUserDetails =========================");
}
```
I used SecurityContextHolder to obtain loggedUsername.

##### Method postHandle()
After handling a request, the model parameters are available, so we may access them to change values or add new ones. In order to do that, we use the overridden postHandle() method:

```Java
@Override
public void postHandle(
  HttpServletRequest req, 
  HttpServletResponse res,
  Object o, 
  ModelAndView model) throws Exception {
    
    if (model != null && !isRedirectView(model)) {
        if (isUserLogged()) {
        addToModelUserDetails(model);
    }
    }
}
```

First of all, it's better to check if the model is not null. It will prevent us from encountering a NullPointerException.

Moreover, I checked if a View is not an instance of RedirectView.

There is no need to add/change parameters after the request is handled and then redirected, as immediately, the new controller will perform handling again. To check if the view is redirected, I used the following method:

```Java
public static boolean isRedirectView(ModelAndView mv) {
    String viewName = mv.getViewName();
    if (viewName.startsWith("redirect:/")) {
        return true;
    }
    View view = mv.getView();
    return (view != null && view instanceof SmartView
      && ((SmartView) view).isRedirectView());
}
```

Finally, I checked again if a user is logged, and if yes, I added parameters to Spring model:

```Java
private void addToModelUserDetails(ModelAndView model) {
    log.info("=============== addToModelUserDetails =========================");
    
    String loggedUsername = SecurityContextHolder.getContext()
      .getAuthentication().getName();
    model.addObject("loggedUsername", loggedUsername);
    
    log.trace("session : " + model.getModel());
    log.info("=============== addToModelUserDetails =========================");
}
```

And to add the created Interceptor into Spring configuration,I had to override addInterceptors() method inside MvcConfigure class that implements WebMvcConfigurer:

```Java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new UserInterceptor());
}
```




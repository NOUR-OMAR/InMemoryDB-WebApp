# InMemoryDB-WebApp

# ***Introduction***

## In-memory database
An in-memory database (IMDB) is a computer system that stores and retrieves data records that reside in a computer’s main memory, e.g., random-access memory (RAM). With data in RAM, IMDBs have a speed advantage over traditional disk-based databases that incur access delays since storage media like hard disk drives and solid-state drives (SSD) have significantly slower access times than RAM. This means that IMDBs are useful for when fast reads and writes of data are crucial.

## Project description:

In this project I'm supposed to Convert the DB to be web-based , the database must handle CRUD (Create, Read (search) , Update, Delete) operations For an entity with a primary key (Student, Ticket, TODO item…etc). One or more records could be affected by any operation. The DB server should be multi-threaded, and gives access only to the authorized people. The In-memory DB size has a maximum number, so caching should be applied to give the maximum performance – the rest of the records are stored on HDD. In-memory DB supports persisting all the DB on HDD, in case DB is switched off. Efficient data structures (Non-blocking data structures) should be used to result in the best optimization for the In-memory DB speed and memory size used. the server side should be web services API or MVC using Servlets/JSP, and the client side should be a web application.

## Requirements:

1- Convert the DB to be web-based i.e. the server side should be web
services API or MVC using Servlets/JSP, and the client side should be a
web application.

2- Implement proper security/ authorization for the DB access.

3- Implement CI/CD in your project.

4- In addition to the points defended in Project 1, extend your defense to include “Effective Java” points, and DevOps.

5- Extend the DB design to include another entity (table), that has a relation with the first table. i.e. primary key/ foreign key. 

------------------------------------------------------------------------------------
# Back-End :
Back end is implemented using Java.

## In-MemoryDB Implementation:

### Database:

We have 3 tables in the database :

1- Employees table with 4 columns : id(primary key), name, salary ,departmentId (foreign key)

2- Departments table with 3 columns: id(primary key), name, location.

employees table and department tables are related to each other, they are related by departmentId in employees table referencing the primary key id of the departments table .

3-Users table with 4 columns : username(primary key), id(foreign key),password,role.
employees table and users tables are related to each other, they are related by id in users table referencing the primary key id of the employees table .
roles column in users table to define the role for each user to access the DB .


I implemented the idea of in-memory Database using ConcurrentHashMap.

```java
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
I used ConcurrentHashMap for the implementation because:

- ConcurrentHashMap class is thread-safe i.e. multiple threads can operate on a single object without any complications. It is a thread-safe without synchronizing the whole map.
-	At a time any number of threads are applicable for a read operation without locking the ConcurrentHashMap object which is not there in HashMap.
-	In ConcurrentHashMap, at a time any number of threads can perform retrieval operation but for updated in the object, the thread must lock the particular segment in which the thread wants to operate. This type of locking mechanism is known as Segment locking or bucket locking. Hence at a time, 16 update operations can be performed by threads.
-	Inserting null objects is not possible in ConcurrentHashMap as a key or value.
-	Reads can happen very fast while the write is done with a lock on segment level or bucket level.
-	There is no locking at the object level.
-	ConcurrentHashMap does not allow NULL values, so the key can not be null in ConcurrentHashMap which helps to implement the idea of primary key that must not be null.

### Once the database is initialised the records which are saved in employees csv file ,departments csv file and users csv file, are all stored in allEmployees,allDepartments,allUsers ConcurrentHashMap which will present the idea of In-Memory DataBase.

#### Tables in the disk:

I saved the records in a csv file which represents the disk storge, The CSV stands for Comma-Separated Values. It is a simple file format which is used to store tabular data in simple text form, such as a spreadsheet or database. The files in the CSV format can be imported to and exported from programs (Microsoft Office and Excel) which store data in tables. The CSV file used a delimiter to identify and separate different data token in a file. The CSV file format is used when we move tabular data between programs that natively operate on incompatible formats. There are following ways to read CSV file in Java. The default separator of a CSV file is a comma (,) but I used semicolon, there is no reason for that.
Employees table as saved in csv file , where the first column is the id which represents the primary key, the second column is the employee name , the third represents the salary, and the fourth represents the departmentId which is a foreign key in employees table referencing the primary key id in departments table ,which is saved in a csv file and has 3 columns which are id as a primary key, name of the department and the location .


#### Synchronization 

Synchronization in java is the capability to control the access of multiple threads to any shared resource.
Java Synchronization is better option where we want to allow only one thread to access the shared resource. So only be executed by one thread at a time. 

1) the synchronized keyword in Java provides locking, which ensures mutually exclusive access to the shared resource and prevents data race.

2) synchronized keyword also prevents reordering of code statement by the compiler which can cause a subtle concurrent issue if we don't use synchronized keyword.

3) synchronized keyword involves locking and unlocking.

Because multithreading is used and the application can have many clients that can read/write to the database, synchronized keyword is used whenever there is an access to the database whether to initialize it or create records. [thread saftey in singlteon](#1-Singelton-design-pattern)
All synchronized blocks synchronized on the same object can only have one thread executing inside them at a time. All other threads attempting to enter the synchronized block are blocked until the thread inside the synchronized block exits the block.
for example:

in adding and removing:

```Java

public void putInEmployeesTable(Employee employee) throws IOException {
        synchronized (getTableLRUCache()) {

..
            }

            ..

        }
        writeInLogger();
    }
    
    public void removeFromTableCache(int id) throws IOException {
        synchronized (getTableLRUCache()) {
..
}
..
}
    
```

-----------------------------------------------------------------------------------------------------------

# Spring Framework

Spring: Spring Framework is the most popular application development framework of Java. The main feature of the Spring Framework is dependency Injection or Inversion of Control (IoC). With the help of Spring Framework, we can develop a loosely coupled application.

Spring Boot: Spring Boot is a module of Spring Framework. It allows us to build a stand-alone application with minimal or zero configurations. 
It aims to shorten the code length and provide the easiest way to develop Web Applications.
The primary feature of Spring Boot is Autoconfiguration. It automatically configures the classes based on the requirement.
It helps to create a stand-alone application with less configuration.
Spring Boot offers embedded server such as Jetty and Tomcat, etc.
Spring Boot comes with the concept of starter in pom.xml file that internally takes care of downloading the dependencies JARs based on Spring Boot Requirement.

### MVC in the code :

#### M-Model :

I have 4 models :Department, User , Admin and Employee

#### V-View : the views are in jsp 

JavaServer Pages (JSP) is a Java standard technology that enables you to write dynamic, data-driven pages for your Java web applications. JSP is built on top of the Java Servlet specification. The two technologies typically work together, especially in older Java web applications. From a coding perspective, the most obvious difference between them is that with servlets you write Java code and then embed client-side markup (like HTML) into that code, whereas with JSP you start with the client-side script or markup, then embed JSP tags to connect your page to the Java backend.

and I used JSTL library to make use of the data comes from the controller

The JSP Standard Tag Library (JSTL) represents a set of tags to simplify the JSP development.

#### Advantage of JSTL

1. Fast Development JSTL provides many tags that simplify the JSP.
2. Code Reusability We can use the JSTL tags on various pages.
3. No need to use scriptlet tag It avoids the use of scriptlet tag. <%  java source code %>  


#### C-Controller :
These are the following controller classes :
![image](https://user-images.githubusercontent.com/77013882/132759547-f5cd65dc-2f98-4178-a066-4c0f64585b42.png)

In Spring Boot, the controller class is responsible for processing incoming requests, preparing a model, and returning the view to be rendered as a response.

The controller classes in Spring are annotated by the @Controller annotation. this mark controller classes as a request handler to allow Spring to recognize it as a RESTful service during runtime.

#### The @Controller Annotation :

The @Controller annotation is a specialization of the generic stereotype @Component annotation, which allows a class to be recognized as a Spring-managed component.

The @Controller annotation extends the use-case of @Component and marks the annotated class as a business or presentation layer. When a request is made, this will inform the DispatcherServlet to include the controller class in scanning for methods mapped by the @RequestMapping , @GetMapping annotations.

for example AdminController class :

```Java
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
```
---------------------------------------------------------------------------------------------------------------------------------------

## Authorization & Authentication:

### I used Spring Security to redirect to different types of pages after Login:

1- The Spring Security Configuration

Spring Security provides a component that has the direct responsibility of deciding what to do after a successful *authentication* – 
_the AuthenticationSuccessHandler_.
 
#### Firstly, users and their roles needs to be configured.

In my code I implemented UserDetailService with 2 types of users, each having one single role.
I have an admin , and employees 
- admin has the role of ADMIN,
- and every employee has the role of EMPLOYEE


So to get the users roles from users table in the database I Implemented UserDetails and UserDetailsService:
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
In this class , the method  ```public UserDetails loadUserByUsername(String username)``` will be invoked by Spring Security when authenticating the users. It will use the readUser(username) method that comes from UserTableDAO to get the user with its information using its username from the users table in the database ,and redirect it to NewUserDetails to make use of the information that are loaded and get the authorities of the users.


## Configure Spring Security Authentication & Authorization :

### And to connect all the pieces together, Spring Security configuration class is implemented as follows :

I configure the basic @Configuration ``` SecurityConfigurer ``` class that extends ``` WebSecurityConfigurerAdapter ```  .

```Java
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {


    public SecurityConfigurer() {
        super();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        auth.authenticationProvider(getAuthenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login**", "/register**").permitAll()
                .antMatchers("/").hasAuthority(Roles.ADMIN.getRole())
                .antMatchers("/employee").hasAnyAuthority(Roles.EMPLOYEE.getRole(), Roles.ADMIN.getRole())
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .successHandler(getAuthenticationSuccessHandler())
                .and().logout().permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public UserDetailsService getUserDetails() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(getUserDetails());
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return new UrlAuthenticationSuccessHandler();
    }


}

```
these methods are needed to configure an authentication provider :

```Java 
@Bean
    public UserDetailsService getUserDetails() 
    
@Bean
    public BCryptPasswordEncoder getPasswordEncoder()
@Bean
    public DaoAuthenticationProvider getAuthenticationProvider()
@Override
    protected void configure(AuthenticationManagerBuilder auth)
     
``` 

And in this method I configure HTTP Security for authentication and authorization then redirect the users based on their roles . Where the ``` getAuthenticationSuccessHandler ```  is called inside ``` successHandler ``` .

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

I give the Admin the access to every page ,and the employee to only the employee page .

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
  protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
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
        roleTargetUrlMap.put(Roles.ADMIN.getRole(), "/adminView");
        roleTargetUrlMap.put(Roles.EMPLOYEE.getRole(), "/employeeView");

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
as we can see admin will be redirected to "/adminView" url ,

![Screenshot (185)](https://user-images.githubusercontent.com/77013882/132761827-cf977f39-87d8-4445-a9aa-16dcdef43dd3.png)



and user to "/employeeView" url.

![Screenshot (189)](https://user-images.githubusercontent.com/77013882/132761776-cefc44bd-2207-4278-b94d-130c47164857.png)



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
-------------------------------------------------------------------------------------------------------------

# DevOps :

DevOps is a set of practices that works to automate and integrate the processes between software development and IT teams, so they can build, test, and release software faster and more reliably.I used Jenkins as DevOps engine.

What is Jenkins?
Jenkins is an open source automation server. It helps automate the parts of software development related to building, testing, and deploying, facilitating continuous integration and continuous delivery. It is a server-based system that runs in servlet containers such as Apache Tomcat.

I worked with Jenkins and Tomcat as ec2 instances on AWS :

![image](https://user-images.githubusercontent.com/77013882/132761370-3e11e927-990b-4872-aae8-9939c8f98c18.png)

when I commit and push any change on the back end to the GitHub, the jenkins server will build ,test and deploy it on the tomcat container.

---------------------------------------------------------------------------------------------------------

# Effective Java Code Principles
 
## Effective Java Book : 

THIS book is designed to help you make effective use of the Java programming language and its fundamental libraries: java.lang, java.util, and java.io, and subpackages such as java.util.concurrent and java.util.function. Other libraries are discussed from time to time.

This book consists of ninety items, each of which conveys one rule. The rules capture practices generally held to be beneficial by the best and most experienced programmers. The items are loosely grouped into eleven chapters, each covering one broad aspect of software design. The book is not intended to be read from cover to cover: each item stands on its own, more or less. 


### Code principles:

#### Creating and Destroying Objects :

1. Item 3: Enforce the singleton property with a private constructor or an enum type:

   a. Defention: A singleton is simply a class that is instantiated exactly once [Gamma95]. Singletons typically represent either a stateless object such as a function (Item 24) or a system component that is intrinsically unique. By providing a private constructor you prevent class instances from being created in any place other than this very class.

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
 
   b. Implementation : since I used spring boot ,Dependency Injection is a fundamental aspect of the Spring framework, through which the Spring container “injects” objects into other objects or “dependencies”. Simply put, this allows for loose coupling of components and moves the responsibility of managing components onto the container. 
   
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
returned by toString so the users of the object don't need to parse the output of the toString.

   b. Implementation : Any class definition may be annotated with @ToString to let lombok generate an implementation of the toString() method. By default, it'll print your class name, along with each field, in order, separated by commas. for example in Employee class :
 
 ```Java
@ToString
@Data
public class Employee extends User {
...
}
```

#### Classes and Interfaces :

1. Item 15: Minimize the accessibility of classes and members : 

   a. Definition : The single most important factor that distinguishes a well-designed component from a poorly designed one is the degree to which the component hides its internal data and other implementation details from other components. A well-designed component hides all its implementation details, cleanly separating its API from its implementation. Components then communicate only through their APIs and are oblivious to each others’ inner workings. This concept, known as information hiding or encapsulation, is a fundamental tenet of software design.
 
   b. Implementation :I set the fields to be private and annotated them with @Getter ,and @Setter annotations to encapsualtes them as shown below :
  
  ```Java
  
    @Getter
    private static final LRUCache<Integer, Object> tableLRUCache = new LRUCache<>(CacheMaxSize.CACHE_MAX_SIZE.getSize());

    @Getter
    private static final LRUCache<String, User> usersLRUCache = new LRUCache<>(CacheMaxSize.CACHE_MAX_SIZE.getSize());

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

   b. Implementation : Any class definition may be annotated with @Data to let lombok generate an implementation of accessor methods
(getters) and mutators (setters). 

   
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
 
1. Item 28: Prefer lists to arrays :

   a. Definition:
Arrays differ from generic types in two important ways. First, arrays are covariant. This scary-sounding word means simply that if Sub is a subtype of Super, then the array type Sub[] is a subtype of the array type Super[]. Generics, by contrast, are invariant: for any two distinct types Type1 and Type2, List<Type1> is neither a subtype nor a supertype of List<Type2> . You might think this means that generics are deficient, but arguably it is arrays that are deficient. 
 
   b. Implementation: I didn't use arrays in the code , and I used Lists when needed :
 
 ```Java
  @Override
    public List<Employee> selectAll() {
        return new ArrayList<>(database.getEmployeesTable().values());
    }
```
 
#### Enums and Annotations:
 
 1. Item 34: Use enums instead of int constants :
 
  a. Definition : An enumerated type is a type whose legal values consist of a fixed set of constants.Enums provide compile-time type safety. 
 
  b. Implementation : I replaced the usage of (int constant) with enums as shown below :
 
 ```Java
 public enum RecordLength {


    EMPLOYEES_RECORD_LENGTH(4),
    DEPARTMENT_RECORD_LENGTH(3),
    USERS_RECORD_LENGTH(4);
    private int length;

    RecordLength(int length) {
        this.length = length;
    }
    public int getRecordLength() {
        return length;
    }
}
```
 
####  Methods :
 
 1. Item 51: Design method signatures carefully :
 
    1- Choose method names carefully : names should always obey the standard naming conventions 
 
    2- Don’t go overboard in providing convenience methods : Every method should “pull its weight.” Too many methods make a class difficult to learn, use, document, test, and maintain. 
 
    3- Avoid long parameter lists. Aim for four parameters or fewer.
 Some of the rules are discussed in clean code section in details.
 
 
#### General prgramming :

1. Item 58: Prefer for-each loops to traditional for loops :
 
   a. Definition :  The for-each loop (officially known as the “enhanced for statement”) solves all of these problems. It gets rid of the clutter and the opportunity for error by hiding the iterator or index variable. The resulting idiom applies equally to collections and arrays, easing the process of switching the implementation type of a container from one to the other.
 
   b. Implementation : I used for-each when needed in my code , an example is shown below : 
 
```Java
  for (Map.Entry<Integer, Employee> employee : database.getEmployeesTable().entrySet()) {
            if (employee.getValue().getSalary() < salary) {
                employeeHashtable.put(employee.getKey(), employee.getValue());
            }
        }
```                                                       
2. Item 59: Know and use the libraries : 
                                                         
   a. Definition: By using a standard library, you take advantage of the knowledge of the experts who wrote it and the experience of those who used it before you.
                                                         
   b. Implemntation : I used libraries almost everywhere in my code .
                                                         
3. Item 64: Refer to objects by their interfaces :
                                                         
   a. Definition:  If appropriate interface types exist, then parameters, return values, variables, and fields should all be declared using interface types. The only time you really need to refer to an object’s class is when you’re creating it with a constructor.
                                                         
   b. Implementation : for example :
                                                         
```Java
  private static ConcurrentHashMap<Integer, Department> allDepartments;
 ```
 
 #### Exceptions : 
 
1. Item 69: Use exceptions only for exceptional conditions :
 
   a. Defintion : Exceptions are, as their name implies, to be used only for exceptional conditions; they should never be used for ordinary control flow. 
 
    b. Implementation : I used I/O exceptions to handle reading and writing exceptions on files.
 
 ```Java
 public interface FileHandler {
    void initialize() throws IOException;

    void write(String fileName) throws IOException;
}
 ```
2. Item 75: Include failure-capture information in detail messages :
 
   a. Definition: To capture a failure, the detail message of an exception should contain the values of all parameters and fields that contributed to the exception.  
 
   b. Implementation : I write specific messages for failure to be displayed on the console , but since it is a web app I need to update it to be displayed on the client side . for example :
 
 ```Java
display("Can't update, employee with id " + employee.getId() + " doesn't exist.");
```
 
 #### Concurrency: 
 
 1. Item 78: Synchronize access to shared mutable data :
 
    a. Definetion : The synchronized keyword ensures that only a single thread can execute a method or block at one time. Synchronization is required for reliable communication between threads as well as for mutual exclusion. This is due to a part of the language specification known as the memory model, which specifies when and how changes made by one thread become visible to others.
 
    b. Implementation : To achieve thread saftey I used synchronized keyword whenever I have to access the database as shown below :
 
``` Java
  public void putInDepartmentsTable(Department department) throws IOException {
        synchronized (getTableLRUCache()) {

         ..
        }

     
    }
 
 public void removeFromDepartmentsTable(int id) throws IOException {
        synchronized (getAllDepartments()) {
 ..
        }
        
    }
 ```                       

----------------------------------------------------------------------------------------------------------------

# Clean Code
>Even bad code can function. But if code isn't clean, it can bring a development organization to its knees.

In this section I will discuss all the code smells that are mentioned in Robert Martin’s Clean Code book and how I tried to avoid them and satisfy clean code principles.
## 1.	*COMMENTS*:

•	Inappropriate comments: It is inappropriate for a comment to hold information better held in a different kind of system such as your source code control system, your issue tracking system, or any other record-keeping system. Comments should be reserved for technical notes about the code and design.

•	Obsolete comment: A comment that has gotten old, irrelevant, and incorrect is obsolete. Comments get old quickly. It is best not to write a comment that will become obsolete. If you find an obsolete comment, it is best to update it or get rid of it as quickly as possible. Obsolete comments tend to migrate away from the code they once described. They become floating islands of irrelevance and misdirection in the code.

•	Redundant Comment: A comment is redundant if it describes something that adequately describes itself.

•	Poorly Written Comment: A comment worth writing is worth writing well. If you are going to write a comment, take the time to make sure it is the best comment you can write. Choose your words carefully. Use correct grammar and punctuation. Do not ramble. Do not state the obvious. Be brief.

•	Commented-Out Code.
These are the comment smell that Uncle bob listed, I tried to avoid writing comments, but when needed I made sure they are brief, well written, not redundant, related to the code, and describing code.
>The Comments should say things that the code cannot say for itself.

## 2.*ENVIRONMENT:*

•	Build Requires more than one step. Building a project should be a single trivial operation. You should not have to check many little pieces out from source code control You should not have to search near and far for
all the various little extra JARs, XML files, and other artifacts that the system requires.

•	Tests Require more than one step. You should be able to run all the unit tests with just one command. In the best case you can run all the tests by clicking on one button in your IDE.

How I avoid those smells: The project is a Maven Project, Maven is a popular open source build tool for enterprise Java projects, designed to take much of the hard work out of the build process. Maven uses a declarative approach, where the project structure and contents are described. This reduces the time needed to write and maintain build scripts. And helps to run all tests from the IDE in the test's directory.

## 3.*FUNCTIONS:* _FUNCTIONS SHOULD DO ONE THING. THEY SHOULD DO IT WELL. THEY SHOULD DO IT ONLY_.

•	Too many arguments: functions should have a small number of arguments. No argument is best, followed by one, two, and three. More than three is very questionable and should be avoided with prejudice.

•	Output Arguments: Output arguments are counterintuitive. Readers expect arguments to be inputs, not outputs. If your function must change the state of something, have it change the state of the object it is called on.

•	Flag Arguments: Boolean arguments loudly declare that the function does more than one thing. They are confusing and should be eliminated. 

•	Dead Function: Methods that are never called should be discarded. Keeping dead code around is wasteful.

  - How I avoid those smells: I tried to avoid them by making all the methods to take at most three arguments,
      not using any output or flag arguments, and I made sure all the methods are used and there are no dead methods.
      
     - ***Some rules from clean code***:
     
        -	Function should be SMALL
        
                "The first rule of functions is that they should be small. "
                  I tried to make the methods small as I could ,
                  and if there is a common logic between two methods 
                  I tried to extract it to a third method to avoid duplication and make the method smaller.
         - Blocks and Indenting
         
                 "This implies that the blocks within if statements, else statements, while statements, and so on should be one line long. Probably that line should be a function call. Not only does this keep the enclosing function small, but it also adds documentary value because the function called within the block can have a nicely descriptive name."
          -	SWITCH STATEMENTS
          
                 'It’s hard to make a small switch statement.
                 Even a switch statement with only two cases is larger than I’d like a single block or function to be. It’s also hard to make a switch statement that does one thing.
                 By their nature, switch statements always do N things.
                 Unfortunately, we can’t always avoid switch statements, but we can make sure that each switch statement is buried in a low-level class and is never repeated. We do this, of course, with polymorphism.'
                 I used the switch statement in the ``OperationFactory`` class and ``FilterSalaryOperation`` class and their logic is not repeated anywhere 


## 4.*GENERAL*

•	 _Multiple Languages in One Source File_: Today’s modern programming environments make it possible to put many different languages into a single source file. This is confusing at best and carelessly sloppy at worst. 
The ideal is for a source file to contain one, and only one, language. 
   -	How I avoid this smell: there is only one language in the source file which is java .
   
•	_Obvious Behavior Is Unimplemented_: any function or class should implement the behaviors that another programmer could reasonably expect.
   - How I avoid this smell: I implemented the behaviors the programmer expected. 

•	_Incorrect Behavior at the Boundaries_: Every boundary condition, every corner case, every quirk and exception represent something that can confound an elegant and intuitive algorithm. Don’t rely on your intuition. Look for every boundary condition and write a test for it.
- How I avoid this smell: I tested every single condition and made sure it works as expected.

•	 _Duplication_: Don’t Repeat Yourself. “Once, and only once.”
  - How I avoid this smell: I didn't duplicate the logic and I implemented the design patterns to not DRY.

•	_Code at Wrong Level of Abstraction_:
It is important to create abstractions that separate higher level general concepts from lower level detailed concepts. All the lower level concepts must be in the derivatives and all the higher level concepts to be in the base class.
  - How I avoid this smell: I made interfaces to make sure that constants, variables, or utility functions that pertain only to the detailed implementation should not be present in the base class. The base class should know nothing about them. And The design patterns I used are following this rule.
  
•	_Base Classes Depending on Their Derivatives_: The higher level base class concepts can be independent of the lower level derivative class concepts. Therefore, if  base classes mentioning the names of their derivatives, a problem is suspected . In general, base classes should know nothing about their derivatives.
  - How I avoid this smell: base classes didn't dependent on their derivatives .
  
•	_Too Much Information_: Well-defined modules have very small interfaces that allow you to do a lot with a little. Poorly defined modules have wide and deep interfaces that force the programmer to use many different gestures to get simple things done. A well-defined interface does not offer very many functions to depend upon, so coupling is low. A poorly defined interface provides lots of functions that must be called, so coupling is high.
  - How I avoid this smell: I tried to limit the number of methods in the interface to make it small .I tried to hide data, methods, constants  as much as possible and tried not to create classes with lots of methods or lots of instance variables. 
  
•	_Dead Code_: is the code that isn’t executed. The problem with dead code is that after a while it starts to smell. The older it is, the stronger and sourer the odor becomes. This is because dead code is not completely updated when designs change. It still compiles, but it does not follow newer conventions or rules. It was written at a time when the system was different. 
  - How I avoid this smell: I deleted all the unexecuted bodies .
  
•	_Vertical Separation_ : Variables and function should be defined close to where they are used. Local variables should be declared just above their first usage and should have a small vertical scope. We don’t want local variables declared hundreds of lines distant from their usages.

 - How I avoid this smell: since the classes are small , there is no much lines separates the methods from the variables.
 
•	_Inconsistency_ :If you do something a certain way, do all similar things in the same way. Be careful with the conventions to choose, and once chosen, be careful to continue to follow them.
 - How I avoid this smell: Consistent methods and names are used.
 
•	_Clutter_:  Variables that aren’t used, functions that are never called, comments that add no information, and so forth. All these things are clutter and should be removed. Keep your source files clean, well organized, and free of clutter.
 - How I avoid this smell: nothing that may make a clutter is left, I delete all of them.
 
•	_Artificial Coupling_ :  artificial coupling is a coupling between two modules that serves no direct purpose. It is a result of putting a variable, constant, or function in a temporarily convenient, though inappropriate, location.
 - How I avoid this smell: I tried to decouple the classes as much as I can.

•	_Feature Envy_ : The methods of a class should be interested in the variables and functions of the class they belong to, and not the variables and functions of other classes. When a method uses accessors and mutators of some other object to manipulate the data within that object, then it envies the scope of the class of that other object. 
 - How I avoid this smell: I tried to eliminate feature envy by applying single responsibility and open closed principles.

•	_Selector Arguments_: Each selector argument combines many functions into one. Selector arguments are just a lazy way to avoid splitting a large function into several smaller functions. 
 - How I avoid this smell: I didn't use any selector argument at all, in order to make the methods small as well as to avoid combining the methods in one method and making a multipurpose methods.
 
•	_Function Names Should Say What They Do_ : If you have to look at the implementation (or documentation) of the function to know what it does, then you should work to find a better name or rearrange the functionality so that it can be placed in functions with better names.
  - How I avoid this smell: I tried to well-name my methods as well as follow the naming conventions.

•	_Follow Standard Conventions_:  follow a coding standard based on common industry norms. This coding standard should specify things like where to declare instance variables; how to name classes, methods, and variables; where to put braces; and so on. So there is no need for a document to describe these conventions because their code provides the examples.
 - How I avoid this smell: I followed the Java language coding standards presented in the Java Language Specification , 
 from Sun Microsystems, Inc. Major contributions are from Peter King, Patrick Naughton, Mike DeMoney, Jonni Kanerva, Kathy Walrath, and Scott Hommel.

•	_Replace Magic Numbers with Named Constants_: In general it is a bad idea to have raw numbers in your code. You should hide them behind well-named constants.
 - How I avoid this smell: I put all the fixed numbers and statements In a Constant class .
 
•	_Be Precise_: When you decide in your code, make sure your decision is precise. Know why you have made it and how you will deal with any exceptions.
 - How I avoid this smell:  One of the cases of this rule is calling a method that might return null I should check for the null value.

•	_Encapsulate Conditionals_: Boolean logic is hard enough to understand without having to see it in the context of an if or while statement. 
 - How I avoid this smell:  I tried to Extract methods that explain the intent of the conditional.

•	_Avoid Negative Conditionals_: Negatives are just a bit harder to understand than positives. 
 - How I avoid this smell:  when possible, conditionals should be expressed as positives.
 
•	_Functions Should Do One Thing_ : It is often tempting to create functions that have multiple sections that perform a series of operations. Functions of this kind do more than one thing, and should be converted into many smaller functions, each of which does one thing.
 - How I avoid this smell:  I tried to extract as much methods as possible in order to make every method do a single thing.
 
•	_Encapsulate Boundary Conditions_ : Boundary conditions are hard to keep track of. 
 - How I avoid this smell:  I tried to Put the processing for them in one place.

•	_Keep Configurable Data at High Levels_: If you have a constant such as a default or configuration value that is known and expected at a high level of abstraction, do not bury it in a low-level function. 
 - How I avoid this smell:  I put all the constant data in Constants class.
 
  - ***Some rules from clean code***:
  
    - Formatting:
    
        'First of all, let’s be clear. Code formatting is important. It is too important to ignore and it is too important to treat religiously. 
        Code formatting is about communication, and communication is the professional developer’s first order of business. 
        I used the Reformat command to reformat the code after every single update.

   - Error Handling:
   
       1.	USE EXCEPTIONS RATHER THAN RETURN CODES
       2.	WRITE YOUR TRY-CATCH-FINALLY STATEMENT FIRST


## 5. *JAVA*

•	_Don't Inherit Constants_ :Writing constants in an interface and then gaining access to those constants by inheriting that interface.
 - How I avoid this smell: I didn't put constants in interfaces to and inherit them.
 
•	_Constants versus Enums_ : use enums instead of constants.
  - How I avoid this smell: I used enums and constants when needed.
  
## 6. *NAMES*
•	_Choose Descriptive Names_: Make sure the name is descriptive. Remember that meanings tend to drift as software evolves, so frequently reevaluate the appropriateness of the names you choose.This is not just a “feel-good” recommendation. Names in software are 90 percent of what make software readable. You need to take the time to choose them wisely and keep them relevant. Names are too important to treat carelessly.
  -	How I avoid this smell: I tried to choose descriptive names as much as I can.
  
•	_Choose Names at the Appropriate Level of Abstraction_:
Don’t pick names that communicate implementation; choose names the reflect the level of abstraction of the class or function you are working in.
 - How I avoid this smell: I tried to choose general names in the interfaces.
 
•	_Unambiguous Names_: Choose names that make the workings of a function or variable unambiguous.
•	_Avoid Encodings_ :Names should not be encoded with type or scope information. Prefixes such as m_ or f are useless in today’s environments.
 -How I avoid those smells: I didn't choose ambiguous and encoded names.

### **Naming conventions in java**:

#### Classes and Interfaces :

•	Class names should be nouns, in mixed case with the first letter of each internal word capitalised. Interfaces name should also be capitalised just like class names.
•	Use whole words and must avoid acronyms and abbreviations.

for example:

![image](https://user-images.githubusercontent.com/77013882/127117471-fd9b837f-a12e-461a-85ba-49ffd88954e2.png)


#### Methods :
Methods should be verbs, in mixed case with the first letter lowercase and with the first letter of each internal word capitalised.

for example:

![image](https://user-images.githubusercontent.com/77013882/127117644-90af990e-c0d5-4992-9c41-86a3fbedf20c.png)

#### Variables : Variable names should be short yet meaningful.
•	Variables can also start with either underscore(‘_’) or dollar sign ‘$’ characters.
•	Should be mnemonic i.e, designed to indicate to the casual observer the intent of its use.
•	One-character variable names should be avoided except for temporary variables; Common names for temporary variables are i, j, k, m, and n for integers; c, d, and e for characters.

example of nammes

```java

    private String username;
    private String password;
    
```
#### Constant variables:
•	Should be all uppercase with words separated by underscores (“_”).
•	There are various constants used in predefined classes like Float, Long, String etc.



#### Packages:
The prefix of a unique package name is always written in all-lowercase ASCII letters .

for example:

![image](https://user-images.githubusercontent.com/77013882/127118233-022266af-47fd-46b4-bc97-e3d786902785.png)

--------------------------------------------------


## 6. *TESTS*

I didn't write tests .

-------------------------------------------------------------

## Lombok:

Project Lombok is a java library tool that is used to minimize/remove the boilerplate code and save the precious time of developers during development by just using some annotations. In addition to it, it also increases the readability of the source code and saves space. 

I used lombok tool to increase the application of clean code and reduce code smells as much as possible.

------------------------------------------------------------------------

## Conclusion 
>This list of heuristics and smells could hardly be said to be complete. Indeed, I’m not sure that such a list can ever be complete. But perhaps completeness should not be the goal, because what this list does do is imply a value system. Indeed, that value system has been the goal, and the topic, of this book. Clean code is not written by following a set of rules. You don’t become a software craftsman by learning a list of heuristics. Professionalism and craftsmanship come from values that drive disciplines. 

-------------------------------------------------------------------------------
# Design Patterns
Design patterns are well-proved solution for solving the specific problem/task.
By using the design patterns you can make your code more flexible, reusable and maintainable. It is the most important part because java internally follows design patterns.

## My Implementation: 
### 1-Singelton design pattern:
![image](https://user-images.githubusercontent.com/77013882/127043848-bb495c09-a5f7-4ebd-b373-757436679dc7.png)

Singleton is one of the most widely used creational design pattern to restrict the object created by applications.This ensures that a class has only one instance in the entire project, and the same instance of the object is returned every time the creation process is performed/run.
If it is used in a multi-threaded environment, then the thread-safety of the singleton class is very important.
In real-world applications, resources like Database connections or Enterprise Information Systems (EIS) are limited and should be used wisely to avoid any resource crunch.
So , I implemented the Database class to be a singleton class.
In general, we follow the below steps to create a singleton class:
1.	Create the private constructor to avoid any new object creation with new operator.
2.	Declare a private static instance of the same class.
3.	Provide a public static method that will return the singleton class instance variable. If the variable is not initialized then initialize it or else simply return the instance variable.
#### Using the above steps I have created a singleton class that looks like below:
##### Singelton for Database Class:

```java
public static synchronized Database getDatabase() throws IOException {
        if (database == null) {
            database = new Database();
        }
        return database;
    }
```
And I used synchronized key word to make it thread-safe , so that only one thread can execute this method at a time and access the Database.

### DAO design pattern:
DAO stands for Data Access Object. DAO Design Pattern is used to separate the data persistence logic in a separate layer. This way, the service remains completely in dark about how the low-level operations to access the database is done. This is known as the principle of Separation of Logic.
#### Advantages of DAO pattern
There are many advantages for using DAO pattern. Let’s state some of them here:

-While changing a persistence mechanism, service layer doesn’t even have to know where the data comes from. For example, if you’re thinking of shifting from using MySQL to MongoDB, all changes are needed to be done in the DAO layer only.
-DAO pattern emphasis on the low coupling between different components of an application. So, the View layer have no dependency on DAO layer and only Service layer depends on it, even that with the interfaces and not from concrete implementation.
-As we work with interfaces in DAO pattern, it also emphasizes the style of “work with interfaces instead of implementation” which is an excellent OOPs style of programming.

#### I tried to use DAO as much as i can in my code ;
here is an example for using it for Employee Table
#### ``EmployeeTableDAO`` interface :
#####  provides CRUD (Create, Read, Update, Delete) operations as well as other operations for the table Employee in the database.

```java
public interface EmployeeTableDAO {
    void createEmployee(Employee employee) throws IOException;

    void updateEmployee(Employee employee) throws IOException;

    void deleteEmployee(int id) throws IOException;

    Employee readEmployee(int id);

    Map<Integer, Employee> filterByName(String name);

    Map<Integer, Employee> filterBySalaryGT(int salary);

    Map<Integer, Employee> filterBySalaryLT(int salary);

    Map<Integer, Employee> filterBySalaryEQ(int salary);

    void close() throws IOException;

    List<Employee> selectAll();

}
```

### 3-dependency injection
Since I used Spring boot framework to make web-based project , it implies using the dependecy injection:
Dependency Injection (DI) is a design pattern that removes the dependency from the programming code so that it can be easy to manage and test the application. Dependency Injection makes our programming code loosely coupled.

-----------------------------------------------------------------------------------------------
# S.O.L.I.D
![image](https://user-images.githubusercontent.com/77013882/127044895-a49f1e4d-15e2-46cf-8946-f5ac38f264e3.png)

SOLID principles are object-oriented design concepts relevant to software development. SOLID is an acronym for five other class-design principles: Single Responsibility Principle, Open-Closed Principle, Liskov Substitution Principle, Interface Segregation Principle, and Dependency Inversion Principle.

## S-Single Responsibility:
The Single Responsibility Principle (SRP)2 states that a class or module should have one, and only one, reason to change. This principle gives us both a definition of responsibility, and a guideline for class size. Classes should have one responsibility—one reason to change.
The single-responsibility principle says that these two aspects of the problem are really two separate responsibilities, and should, therefore, be in separate classes or modules. 
I tried to make every class has a single job to do by making the classes as small as I could. And put all the related logic in the same class to make the classes smaller and follow the single responsibility priniciple.
for example strategy pattern which I used ,and model classes implements single responsibility,
class ``Employee ``:
```java
public class Employee extends User {
    private int id;
    private String name;
    private int salary;

    public Employee(String id, String name, String salary) {
        setId(id);
        setSalary(salary);
        this.name = name;
    }

    public Employee() {
    }

    public Employee(String userName, String password) {
        super(userName, password);
    }


    public void setId(String id) {
        setId(Integer.parseInt(id));
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setSalary(String salary) {
        setSalary(Integer.parseInt(salary));
    }

    public void setSalary(int salary) {

        this.salary = salary;
    }

}
```
------------------------------------------
## O-Open/Closed :
The Martin definition of OCP:
Robert C. Martin has defined the OCP in many different writings over the years. A more verbose version has been chosen here to contrast with the brief original:
>Open for extension.” This means that the behavior of the module can be extended. As the requirements of the application change, we are able to extend the module with new behaviors that satisfy those changes. In other words, we are able to change what the module does.
“Closed for modification.” Extending the behavior of a module does not result in changes to the source or binary code of the module. The binary executable version of the module, whether in a linkable library, a DLL, or a Java .jar, remains untouched.

In my implementation , using the strategy pattern implements the idea of OCP ,because programming by interface and not by implementation is a best practice that we can use to design and implement code open to extension.and The context(Menu) is open for extensions and closed for modifications since it does not need to be changed to use new types of strategies.If I want to add new strategies afterwards ("open for extentions") I can do that without the need to change the internals of the context ("closed for modifications").
Also, programming by interface is the key factor of the strategy pattern.
also I tried to use implement interfaces as much as I can.

## L-Liskov substitution :
If S is a subtype of T, then objects of type T may be replaced with objects of type S, without breaking the program.
in Strategy :All concrete strategies implement the same interface and should be substitutable without affecting the system’s correctness.

## I-Interface segregation:
>“Clients should not be forced to depend upon interfaces that they do not use.”

in Strategy: is fulfilled when the strategy base class offers only a small, single-purpose interface .

## D-Dependency inversion principle states:
1- High-level modules should not depend on low-level modules. Both should depend on abstractions.
2- Abstractions should not depend on details. Details should depend on abstractions.
*In Dependency Inversion design pattern [dependency-injection](#3-dependency-injection)

------------------------------------------------------------------------------------------------------
# A.C.I.D
![image](https://user-images.githubusercontent.com/77013882/127051353-5572bc1f-70b9-4ce0-8393-ea42eaa304fb.png)

The ACID properties describe the transaction management well. ACID stands for Atomicity, Consistency, isolation and durability.
Advantage of Transaction Management:
fast performance It makes the performance fast because database is hit at the time of commit.

## A.C.I.D:
1. A-Atomicity means either all successful or none. Transactions are often composed of multiple statements. Atomicity guarantees that each transaction is treated as a single "unit", which either succeeds completely, or fails completely.
 - How I implemented it: If the user enters wrong data type for any argument, the statement will not succeed.
 
2. C-Consistency ensures bringing the database from one consistent state to another consistent state. This prevents database corruption by an illegal transaction, but does not guarantee that a transaction is correct.
 -	How I implemented it: the user is not allowed to make illegal transactions.
 
3. I-Isolation ensures that transaction is isolated from other transaction, A given transaction should appear as though it is running alone in the database. The work of other users must be coordinated with other transactions to maintain this isolated view. The work of other transactions is invisible to the user in a transaction.
 -	How I implemented it: the multithreaded server handles client that access the database and synchronizes their commitments.
 
4.	D-Durability means once a transaction has been committed, it will remain so, even in the event of errors, power loss etc.
IMDBs can be said to lack support for the "durability" portion of the ACID (atomicity, consistency, isolation, durability) properties. Volatile memory-based IMDBs can, and often do, support the other three ACID properties of atomicity, consistency and isolation.
 -	How I handled it: I tried to make a logger file to back up all the transactions and whenever the system crashes all the data in the logger file are saved in the database file(csv file).
 
 ```java
 public interface TransactionLogger {
    void write();

    void writeToCSV();
}
 ```


----------------------------------------------
## How I worked :
 I tried to work in agile way with myself :
 Agile: 
 The Agile software development methodology is one of the simplest and effective processes to turn a vision for a business need into software solutions. Agile is a term used to describe software development approaches that employ continual planning, learning, improvement, team collaboration, evolutionary development, and early delivery. It encourages flexible responses to change.
 
 ![image](https://user-images.githubusercontent.com/77013882/132762043-0fd195c4-fb18-4d23-8ac4-d76bafb7a06e.png)
1- I put the requirementes as issues on github :
![image](https://user-images.githubusercontent.com/77013882/132762545-0188860f-ca2a-40e3-ad03-20f7ebd521aa.png)
2- and I add some tags to give me some information about my progress :
 ![image](https://user-images.githubusercontent.com/77013882/132762596-0703d101-d0f4-4d69-81c1-77ec2ffb794d.png)

 development tag to know that the code is still under development.
 deployed tag is set when I finished writing and testing the code and after the branch is merged to the master.
 
3- I put milestones as well to mark my progress and to see wether I'm following the plan or not :
 
![image](https://user-images.githubusercontent.com/77013882/132762930-59e2911e-61a3-4adf-8be6-8ada5fc8b99b.png)
 
 
 -------------------------------------
## Future Work :
 
 My code still needs more enhancements and as I put them as issues to work on them in the future , such as UI design and some developments and updates on the backend.

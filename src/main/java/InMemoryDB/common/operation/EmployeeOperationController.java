package InMemoryDB.common.operation;

import InMemoryDB.client.model.Employee;
import InMemoryDB.server.database.Database;
import InMemoryDB.server.database.employee_table.EmployeeTableDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class EmployeeOperationController {

    @Autowired
    EmployeeTableDAO employeeTableDAO;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return "/LoginView";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@RequestParam String name, @RequestParam String password, ModelMap modelMap) {
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("password", password);

        if (Database.getAllUsers().containsKey(name))
            return showAdminView(name, password, modelMap);
        // int userId = service.getUserID(name, password);
        //String userLevel = service.getUserLevel(userId);
        // boolean isValidUser = service.validateUser(name, password);
       /* if (isValidUser) {
            modelMap.addAttribute("name", name);
          //  modelMap.addAttribute("id", userId);

            if (userLevel.equals("1")) {

                return showAdminView(name, password, modelMap);

            } else if (userLevel.equals("2")) {

                return showInstructorsView(name, password, modelMap);

            } else if (userLevel.equals("3")) {

                return listGradesForStudent(modelMap, userId);

            }

        } else {
            modelMap.addAttribute("errorMessage", "Invalid Credentials!!");
        }*/
        return loginGet();

    }

    /*public String listGradesForStudent(ModelMap modelMap, int userId) {
        List<Grade> grades = gradeDAO.selectStudentGrades(userId);
        modelMap.addAttribute("listGrades", grades);
        return "GradesListView";
    }
*/
    public String showAdminView(String name, String password, ModelMap modelMap) {

       /* modelMap.addAttribute("name", name);
        modelMap.addAttribute("password", password);
        int userId = service.getUserID(name, password);
        List<Student> students = studentDAO.selectAllStudents();
        modelMap.addAttribute("listStudents", students);
        List<Course> courses = courseDAO.selectAllCourses();
        modelMap.addAttribute("listCourses", courses);
        List<Instructor> instructors = instructorDAO.selectAllInstructors();
        modelMap.addAttribute("listInstructors", instructors);


        modelMap.addAttribute("name", adminDAO.getAdmin(userId).getName());*/


        List<Employee> employees = employeeTableDAO.selectAll();
        modelMap.addAttribute("employees", employees);

        return "new";


    }


}

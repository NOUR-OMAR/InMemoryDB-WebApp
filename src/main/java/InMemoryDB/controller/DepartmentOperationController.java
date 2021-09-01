package InMemoryDB.controller;

import InMemoryDB.model.Department;
import InMemoryDB.database.departments_table.DepartmentsTableDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DepartmentOperationController {


    @Autowired
    DepartmentsTableDAO departmentTableDAO;

    @RequestMapping(value = "/departmentView")
    public String showDepartmentsView(ModelMap modelMap) {

        List<Department> departments = departmentTableDAO.selectAll();
        modelMap.addAttribute("departments", departments);
        return "departmentView";


    }
    @RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
    public String addDepartment(@RequestParam int id, @RequestParam String name, @RequestParam String location, ModelMap modelMap) throws IOException {

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("location", location);
        Department department = new Department(id, name, location);
        departmentTableDAO.createDepartment(department);

        return "redirect:/departmentView";

    }

    @RequestMapping(value = "/updateDepartment", method = RequestMethod.GET)
    public String updateDepartment(@RequestParam int id, @RequestParam String name, @RequestParam String location, ModelMap modelMap) throws IOException {

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("location", location);
        Department department = new Department(id, name, location);
        departmentTableDAO.updateDepartment(department);

        return "redirect:/departmentView";
    }


    @RequestMapping(value = "/deleteDepartment-{id}", method = RequestMethod.GET)
    public String deleteDepartment(@PathVariable int id) throws IOException {
        departmentTableDAO.deleteDepartment(id);

        return "redirect:/departmentView";
    }

    @RequestMapping(value = "/filterByName", method = RequestMethod.GET)
    public String filterDepartmentsByName(@RequestParam String name, ModelMap modelMap) throws IOException {

        List<Department> departments = new ArrayList<>();
        departments.addAll(departmentTableDAO.filterByName(name).values());
        modelMap.addAttribute("departments", departments);

        return "ListDepartmentsView";
    }
    @RequestMapping(value = "/filterByLocation", method = RequestMethod.GET)
    public String filterDepartmentsByLocation(@RequestParam String location, ModelMap modelMap) throws IOException {

        List<Department> departments = new ArrayList<>();
        departments.addAll(departmentTableDAO.filterByLocation(location).values());
        modelMap.addAttribute("departments", departments);

        return "ListDepartmentsView";
    }



    @RequestMapping(value = "/back", method = RequestMethod.GET)
    public String close() throws IOException {
        departmentTableDAO.close();
        return "redirect:/adminView";
    }


}

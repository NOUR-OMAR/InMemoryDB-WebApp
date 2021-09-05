package InMemoryDB.controller;

import InMemoryDB.database.departments_table.DepartmentsTableDAO;
import InMemoryDB.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DepartmentOperationController {


    @Autowired
    DepartmentsTableDAO departmentTableDAO;

    @RequestMapping(value = "/departmentView")
    public ModelAndView showDepartmentsView() throws IOException {
        ModelAndView modelAndView = new ModelAndView("departmentView");

        List<Department> departments = departmentTableDAO.selectAll();
        modelAndView.addObject("departments", departments);
        return modelAndView;


    }

    @RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
    public ModelAndView addDepartment(Department department) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/departmentView");
        modelAndView.addObject("department", department);
        departmentTableDAO.createDepartment(department);
        return modelAndView;

    }

    @RequestMapping(value = "/updateDepartment", method = RequestMethod.GET)
    public ModelAndView updateDepartment(Department department) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/departmentView");
        modelAndView.addObject("department", department);
        departmentTableDAO.updateDepartment(department);

        return modelAndView;
    }


    @RequestMapping(value = "/deleteDepartment-{id}", method = RequestMethod.GET)
    public ModelAndView deleteDepartment(@PathVariable int id) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/departmentView");

        departmentTableDAO.deleteDepartment(id);

        return modelAndView;
    }

    @RequestMapping(value = "/filterByName", method = RequestMethod.GET)
    public ModelAndView filterDepartmentsByName(@RequestParam String name) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListDepartmentsView");

        List<Department> departments = new ArrayList<>(departmentTableDAO.filterByName(name).values());
        modelAndView.addObject("departments", departments);

        return modelAndView;
    }

    @RequestMapping(value = "/filterByLocation", method = RequestMethod.GET)
    public ModelAndView filterDepartmentsByLocation(@RequestParam String location) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListDepartmentsView");

        List<Department> departments = new ArrayList<>(departmentTableDAO.filterByLocation(location).values());
        modelAndView.addObject("departments", departments);

        return modelAndView;
    }


    @RequestMapping(value = "/back", method = RequestMethod.GET)
    public ModelAndView close() throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/adminView");

        departmentTableDAO.close();
        return modelAndView;
    }


}

package InMemoryDB.controller;

import InMemoryDB.database.departments_table.DepartmentsTableDAO;
import InMemoryDB.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/addDepartment")
    public ModelAndView addDepartment(Department department) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/departmentView");
        modelAndView.addObject("department", department);
        departmentTableDAO.createDepartment(department);
        return modelAndView;

    }

    @GetMapping(value = "/updateDepartment")
    public ModelAndView updateDepartment(Department department) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/departmentView");
        modelAndView.addObject("department", department);
        departmentTableDAO.updateDepartment(department);

        return modelAndView;
    }


    @GetMapping(value = "/deleteDepartment-{id}")
    public ModelAndView deleteDepartment(@PathVariable int id) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/departmentView");

        departmentTableDAO.deleteDepartment(id);

        return modelAndView;
    }

    @GetMapping(value = "/filterByName")
    public ModelAndView filterDepartmentsByName(@RequestParam String name) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListDepartmentsView");

        List<Department> departments = new ArrayList<>(departmentTableDAO.filterByName(name).values());
        modelAndView.addObject("departments", departments);

        return modelAndView;
    }

    @GetMapping(value = "/filterByLocation")
    public ModelAndView filterDepartmentsByLocation(@RequestParam String location) throws IOException {
        ModelAndView modelAndView = new ModelAndView("ListDepartmentsView");

        List<Department> departments = new ArrayList<>(departmentTableDAO.filterByLocation(location).values());
        modelAndView.addObject("departments", departments);

        return modelAndView;
    }


    @GetMapping(value = "/back")
    public ModelAndView close() throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/adminView");

        departmentTableDAO.close();
        return modelAndView;
    }


}

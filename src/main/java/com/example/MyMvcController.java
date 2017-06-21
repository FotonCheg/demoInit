package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class MyMvcController {

    public static Long currentEditingPageId = 0L;

    @Autowired
    private Tasks tasksRepo;

    @RequestMapping(value = "/credentials", method = RequestMethod.GET)
    @ResponseBody
    public Collection userRole(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Get the username :" + auth.getPrincipal() + " Get the password " + auth.getCredentials() + " Get authorities " + auth.getAuthorities());
        return auth.getAuthorities();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index( Model model) {
        List<Task> list = tasksRepo.findAll();
        Collections.sort(list, Collections.reverseOrder());

        model.addAttribute("list", list);

        return "index";
    }

    @RequestMapping(value = "/app/tasks")
    @ResponseBody
    public List<Task> getTasks(){
        List<Task> list = tasksRepo.findAll();
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    @RequestMapping(value = "/app/tasks/delete", method = RequestMethod.POST)//POST
    @ResponseBody
    public void deleteTask(@RequestParam long id, Model model) {
        System.out.println("Id is - " + id);
        tasksRepo.delete(id);

        List<Task> list = tasksRepo.findAll();
        Collections.sort(list, Collections.reverseOrder());

        model.addAttribute("list", list);
    }

    @RequestMapping(value = "/app/tasks/add", method = RequestMethod.GET)//POST
    public String addNewTask() {
        return "addNewTask";
    }

    @RequestMapping(value = "/app/tasks/adding", method = RequestMethod.POST)//POST
    @ResponseBody
    public String message(@RequestParam String name,
                          @RequestParam String priority) {

        if(name.isEmpty() || priority.isEmpty() ) return null;
        tasksRepo.save(new Task(null, name, Integer.parseInt(priority)) );
        return "/";
    }

    @RequestMapping(value = "/app/tasks/editJSON", method = RequestMethod.GET)
    @ResponseBody
    public void updateEdit( @RequestParam(value = "id", required = true) String id,
                            @RequestParam(value = "name", required = true) String name,
                            @RequestParam(value = "priority", required = true) String priority) {

        if(name.isEmpty() || priority.isEmpty() || id.isEmpty()) return;
        Task task = tasksRepo.findOne(Long.parseLong(id));
        task.setName(name);
        task.setPriority(Integer.parseInt(priority));

        tasksRepo.save(task);
    }

    @RequestMapping(value = "/app/tasks/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {
        List<Task> list = tasksRepo.findAll();
        Collections.sort(list, Collections.reverseOrder());
        currentEditingPageId = id;

       /* model.addAttribute("id", id);
        model.addAttribute("name", list.get((int) id -1).getName());
        model.addAttribute("priority", list.get((int) id -1).getPriority());*/
        return "editPage";
    }

    @RequestMapping(value = "/app/tasks/editing", method = RequestMethod.POST)
    @ResponseBody
    public String update( @RequestParam String name,
                          @RequestParam String priority) {

        if(name.isEmpty() || priority.isEmpty()) return null;
        Task task = tasksRepo.findOne(currentEditingPageId);
        task.setName(name);
        task.setPriority(Integer.parseInt(priority));

        tasksRepo.save(task);
        System.out.println("Id of editing page is - " + currentEditingPageId + " name " + name + " priority is " + priority);
        return "/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)//POST
    public String login() {
        return "login";
    }

}

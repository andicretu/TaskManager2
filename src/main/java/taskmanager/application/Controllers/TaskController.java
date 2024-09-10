package taskmanager.application.Controllers;

import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import taskmanager.application.Models.TaskModel;
import taskmanager.application.Models.UserModel;
import taskmanager.application.Repositories.TaskRepository;
import taskmanager.application.Repositories.UserRepository;

@Controller
public class TaskController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }
    @GetMapping("/taskManager/createTask")
    public String showCreateTaskForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UserModel userModel = userRepository.findByEmail(userEmail);
        model.addAttribute("taskModel", new TaskModel());
        model.addAttribute("userModel", userModel);
        return "taskManagerCreateTask";
    }
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute("taskModel") TaskModel taskModel, @RequestParam("userEmail") String userEmail, Model model) {
        System.out.println("Fetching user with email: " + userEmail);
        UserModel userModel = userRepository.findByEmail(userEmail);
        if (userModel == null) {
            System.out.println("User not found with email: " + userEmail);
            return "error";
        }
        taskModel.setUserModel(userModel);
        taskRepository.save(taskModel);
        return "redirect:/taskManager/showUser/" + userModel.getEmail();
    }
    @GetMapping("/taskManager/show-task/{id}")
    public String showTask(@PathVariable("id") Long id, Model model) {
        TaskModel taskModel = taskRepository.findById(id).orElse(null);
        if (taskModel == null) {
            System.out.println("Task with ID " + id + " not found.");
            return "error";
        }
        model.addAttribute("taskModel", taskModel);
        return "taskManagerShowTask";
    }

    @PostMapping("/taskManager/delete-task/{id}")
    public String deleteTask(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UserModel userModel = userRepository.findByEmail(userEmail);
        taskRepository.deleteById(id);
        return "redirect:/taskManager/showUser/" + userModel.getEmail();
    }
    @GetMapping("/taskManager/edit-task/{id}")
    public String showEditTaskForm(@PathVariable("id") Long id, Model model) {
        TaskModel taskModel = taskRepository.findById(id).orElse(null);
        if (taskModel == null) {
            return "error";
        }
        model.addAttribute("taskModel", taskModel);
        return "taskManagerEditTask";
    }
    @PostMapping("/taskManager/edit-task/{id}")
    public String updateTask(@PathVariable("id") Long id, @ModelAttribute TaskModel taskModel) {
        TaskModel existingTask = taskRepository.findById(id).orElse(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UserModel userModel = userRepository.findByEmail(userEmail);
        if (existingTask == null) {
            return "error";
        }
        existingTask.setTitle(taskModel.getTitle());
        existingTask.setDescription(taskModel.getDescription());
        taskRepository.save(existingTask);
        return "redirect:/taskManager/showUser/" + userModel.getEmail();
    }
}

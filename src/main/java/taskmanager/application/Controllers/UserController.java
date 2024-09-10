package taskmanager.application.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import taskmanager.application.Models.TaskModel;
import taskmanager.application.Models.UserModel;
import taskmanager.application.Repositories.TaskRepository;
import taskmanager.application.Repositories.UserRepository;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registerUser")
    public String showUserRegistrationForm(Model model) {
        model.addAttribute("userModel", new UserModel());
        return "taskManagerRegisterUser";
    }

    @PostMapping("/registerUser")
    public String createNewUser(@ModelAttribute("userModel") UserModel userModel, Model model) {
        String hashedPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(hashedPassword);
        userRepository.save(userModel);
        return "redirect:/taskManager/showUser/" + userModel.getEmail();
    }

    @GetMapping("/taskManager/showUser/{email}")
    public String showUser(@PathVariable String email, Model model) {
        UserModel userModel = userRepository.findByEmail(email);
        if (userModel == null) {
            return "error";
        }
        model.addAttribute("userModel", userModel);
        List<TaskModel> tasksList = taskRepository.findAllByUserModel(userModel);
        model.addAttribute("tasksList", tasksList);
        return "taskManagerShowUser";
    }
}
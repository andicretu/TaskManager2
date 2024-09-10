package taskmanager.application.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import taskmanager.application.Repositories.UserRepository;

@org.springframework.stereotype.Controller
public class TaskManagerApplicationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String showLoginPage() {
        return "taskManagerLogin";
    }
}

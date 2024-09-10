package taskmanager.application.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "tasksTable")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserModel userModel;
    public TaskModel() {
    }
    public TaskModel(Long id, String title, String description, UserModel userModel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userModel = userModel;
    }
    public Long getId() {return id; }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public UserModel getUserModel() {
        return userModel;
    }
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}

package taskmanager.application.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import taskmanager.application.Models.TaskModel;
import taskmanager.application.Models.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<TaskModel, Long> {
    Optional<TaskModel> findById(Long id);
    List<TaskModel> findAllByUserModel(UserModel userModel);
}

package taskmanager.application.Repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import taskmanager.application.Models.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
    UserModel findByEmail(String email);
}

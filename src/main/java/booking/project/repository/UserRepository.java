package booking.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import booking.project.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    User findByUserName(String userName);
}

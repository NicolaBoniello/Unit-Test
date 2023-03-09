package co.develhope.crudTest.unittest1.repositories;

import co.develhope.crudTest.unittest1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

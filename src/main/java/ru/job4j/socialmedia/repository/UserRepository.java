package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmedia.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("""
            select user from User as user
            where user.email = ?1 and user.password = ?2
            """)
    Optional<User> findByLoginAndPassword(@Param("email") String email, @Param("password") String password);
}

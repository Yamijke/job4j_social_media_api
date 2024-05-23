package ru.job4j.socialmedia.repository;

import ru.job4j.socialmedia.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmedia.model.Sub;

import java.util.List;

@Repository
public interface SubRepository extends CrudRepository<Sub, Long> {
    @Query("""
            select s.subscriber from Sub s where s.subscribed.id = :userId
            """)
    List<User> findAllUserSubscribers(@Param("userId") Long userId);
}

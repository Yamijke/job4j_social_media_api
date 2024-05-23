package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.User;

import java.util.List;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {

    @Query("""
            SELECT f.friend FROM Friend f WHERE f.user.id = :userId AND f.status = 'accepted'
            """)
    List<User> findAllUserFriends(@Param("userId") Long userId);
}

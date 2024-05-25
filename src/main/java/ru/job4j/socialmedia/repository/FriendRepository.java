package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.FriendshipStatus;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {

    @Query("""
            select f.friend from Friend f
            where f.user.id = :userId
            and f.status = 'accepted'
            """)
    List<User> findAllUserFriends(@Param("userId") Long userId);

    @Query("""
            select f from Friend f
            where f.user = :user
            and f.friend = :friend AND f.status = :status
            """)
    Optional<Friend> findByUserAndFriendAndStatus(@Param("user") User user,
                                                  @Param("friend") User friend,
                                                  @Param("status") FriendshipStatus status);
}

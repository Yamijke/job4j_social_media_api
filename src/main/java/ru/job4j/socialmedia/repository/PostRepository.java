package ru.job4j.socialmedia.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByCreatedAtBetween(LocalDateTime startAt, LocalDateTime finishAt);

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            update Post post set post.title = :title,
            post.context = :context
            where post.id = :id
            """
    )
    int updateTitleAndContext(@Param("title") String title, @Param("context") String context, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("""
            update Post post set post.imageUrl = null
            where post.id = :id
            """
    )
    void deleteImageUrl(@Param("id") Long id);

    @Query("""
            select post from Post post
            where post.user.id in
            (select s.subscribed.id FROM Sub s where s.subscriber.id = :userId)
            order by post.createdAt DESC
            """
    )
    List<Post> findAllPostsOfSubscribers(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            select post from Post post
            where post.user.id = :userId
            order by post.createdAt DESC
            """
    )
    List<Post> findAllPostsOfUser(@Param("userId") Long userId);
}

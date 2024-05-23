package ru.job4j.socialmedia.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByCreatedAtBetween(LocalDateTime startAt, LocalDateTime finishAt);

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

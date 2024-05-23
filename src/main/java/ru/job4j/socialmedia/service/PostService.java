package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.repository.PostRepository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements CrudService<Post, Long> {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(Post entity) {
        return postRepository.save(entity);
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return postRepository.findById(aLong);
    }

    @Override
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public Post update(Post entity) {
        return postRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        postRepository.deleteById(aLong);
    }

    public List<Post> findByCreatedAtBetween(LocalDateTime startAt, LocalDateTime finishAt) {
        return postRepository.findByCreatedAtBetween(startAt, finishAt);
    }

    public List<Post> findPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public boolean updateTitleAndContext(String title, String context, Long id) {
        return postRepository.updateTitleAndContext(title, context, id) > 0;
    }

    public void deleteImageUrl(Long id) {
        postRepository.deleteImageUrl(id);
    }

    public List<Post> findAllPostsOfSubscribers(Long userId, Pageable page) {
        return postRepository.findAllPostsOfSubscribers(userId, page);
    }
}

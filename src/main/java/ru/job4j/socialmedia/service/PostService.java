package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
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

    @Transactional
    @Override
    public Post update(Post entity) {
        Optional<Post> existingPost = postRepository.findById(entity.getId());
        if (existingPost.isPresent() && existingPost.get().getUser().getId() == entity.getUser().getId()) {
            return postRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Post not found or user does not have permission to update");
        }
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) {
        Optional<Post> existingPost = postRepository.findById(aLong);
        if (existingPost.isPresent() && existingPost.get().getUser().getId() == aLong) {
            postRepository.deleteById(aLong);
        } else {
            throw new IllegalArgumentException("Post not found or user does not have permission to delete");
        }
    }

    public List<Post> findByCreatedAtBetween(LocalDateTime startAt, LocalDateTime finishAt) {
        return postRepository.findByCreatedAtBetween(startAt, finishAt);
    }

    public List<Post> findPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional
    public boolean updateTitleAndContext(String title, String context, Long id, Long userId) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent() && existingPost.get().getUser().getId() == userId) {
            return postRepository.updateTitleAndContext(title, context, id) > 0;
        } else {
            throw new IllegalArgumentException("Post not found or user does not have permission to update");
        }
    }

    @Transactional
    public void deleteImageUrl(Long id, Long userId) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent() && existingPost.get().getUser().getId() == userId) {
            postRepository.deleteImageUrl(id);
        } else {
            throw new IllegalArgumentException("Post not found or user does not have permission to delete image");
        }
    }

    public List<Post> findAllPostsOfSubscribers(Long userId, Pageable page) {
        return postRepository.findAllPostsOfSubscribers(userId, page);
    }
}

package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.repository.PostRepository;

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
}

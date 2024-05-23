package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.FriendRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService implements CrudService<Friend, Long> {
    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public Friend save(Friend entity) {
        return friendRepository.save(entity);
    }

    @Override
    public Optional<Friend> findById(Long aLong) {
        return friendRepository.findById(aLong);
    }

    @Override
    public List<Friend> findAll() {
        return (List<Friend>) friendRepository.findAll();
    }

    @Override
    public Friend update(Friend entity) {
        return friendRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        friendRepository.deleteById(aLong);
    }

    public List<User> findAllUserFriends(Long id) {
        return friendRepository.findAllUserFriends(id);
    }
}

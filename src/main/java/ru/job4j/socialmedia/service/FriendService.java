package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.FriendshipStatus;
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

    @Transactional
    public void handleFriendRequest(User sender, User receiver) {
        Friend friendRequest = new Friend();
        friendRequest.setUser(sender);
        friendRequest.setFriend(receiver);
        friendRequest.setStatus(FriendshipStatus.PENDING);
        friendRepository.save(friendRequest);
    }

    @Transactional
    public void acceptFriendRequest(User receiver, User sender) {
        Optional<Friend> friendRequest = friendRepository
                .findByUserAndFriendAndStatus(sender, receiver, FriendshipStatus.PENDING);
        if (friendRequest.isPresent()) {
            Friend friend = friendRequest.get();
            friend.setStatus(FriendshipStatus.ACCEPTED);
            friendRepository.save(friend);

            Friend reciprocalFriend = new Friend();
            reciprocalFriend.setUser(receiver);
            reciprocalFriend.setFriend(sender);
            reciprocalFriend.setStatus(FriendshipStatus.ACCEPTED);
            friendRepository.save(reciprocalFriend);
        } else {
            throw new IllegalArgumentException("Friend request not found");
        }
    }

    @Transactional
    public void rejectFriendRequest(User receiver, User sender) {
        Optional<Friend> friendRequest = friendRepository
                .findByUserAndFriendAndStatus(sender, receiver, FriendshipStatus.PENDING);
        if (friendRequest.isPresent()) {
            Friend friend = friendRequest.get();
            friend.setStatus(FriendshipStatus.REJECTED);
            friendRepository.save(friend);
        } else {
            throw new IllegalArgumentException("Friend request not found");
        }
    }

    @Transactional
    public void removeFriend(User remover, User friend) {
        Optional<Friend> friendship = friendRepository
                .findByUserAndFriendAndStatus(remover, friend, FriendshipStatus.ACCEPTED);
        if (friendship.isPresent()) {
            friendRepository.delete(friendship.get());

            Optional<Friend> reciprocalFriendship = friendRepository
                    .findByUserAndFriendAndStatus(friend, remover, FriendshipStatus.ACCEPTED);
            reciprocalFriendship.ifPresent(friendRepository::delete);
        }
    }
}

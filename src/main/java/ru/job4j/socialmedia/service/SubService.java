package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Sub;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.SubRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubService implements CrudService<Sub, Long> {
    private final SubRepository subRepository;

    public SubService(SubRepository subRepository) {
        this.subRepository = subRepository;
    }

    @Override
    public Sub save(Sub entity) {
        return subRepository.save(entity);
    }

    @Override
    public Optional<Sub> findById(Long aLong) {
        return subRepository.findById(aLong);
    }

    @Override
    public List<Sub> findAll() {
        return (List<Sub>) subRepository.findAll();
    }

    @Override
    public Sub update(Sub entity) {
        return subRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        subRepository.deleteById(aLong);
    }

    List<User> findAllSubscribersByUser(Long id) {
        return subRepository.findAllUserSubscribers(id);
    }

    @Transactional
    public void subscribeUser(User subscriber, User subscribedTo) {
        Sub subscription = new Sub();
        subscription.setSubscriber(subscriber);
        subscription.setSubscribed(subscribedTo);
        subRepository.save(subscription);
    }

    @Transactional
    public void unsubscribeUser(User subscriber, User subscribedTo) {
        Optional<Sub> subscription = subRepository.findBySubscriberAndSubscribed(subscriber, subscribedTo);
        subscription.ifPresent(subRepository::delete);
    }
}

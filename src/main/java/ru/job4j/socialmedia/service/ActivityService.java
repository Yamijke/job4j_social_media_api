package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Activity;
import ru.job4j.socialmedia.repository.ActivityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService implements CrudService<Activity, Long> {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity save(Activity entity) {
        return activityRepository.save(entity);
    }

    @Override
    public Optional<Activity> findById(Long aLong) {
        return activityRepository.findById(aLong);
    }

    @Override
    public List<Activity> findAll() {
        return (List<Activity>) activityRepository.findAll();
    }

    @Override
    public Activity update(Activity entity) {
        return activityRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        activityRepository.deleteById(aLong);
    }
}

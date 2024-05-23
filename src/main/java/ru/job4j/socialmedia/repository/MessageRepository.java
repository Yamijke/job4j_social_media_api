package ru.job4j.socialmedia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmedia.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}

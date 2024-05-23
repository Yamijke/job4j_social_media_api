package ru.job4j.socialmedia.service;

import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Message;
import ru.job4j.socialmedia.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements CrudService<Message, Long> {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message save(Message entity) {
        return messageRepository.save(entity);
    }

    @Override
    public Optional<Message> findById(Long aLong) {
        return messageRepository.findById(aLong);
    }

    @Override
    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    @Override
    public Message update(Message entity) {
        return messageRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        messageRepository.deleteById(aLong);
    }
}

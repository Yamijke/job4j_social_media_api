package ru.job4j.socialmedia.dto.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.job4j.socialmedia.dto.PostDTO;
import ru.job4j.socialmedia.dto.UserDTO;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.util.List;

public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "name", target = "username")
    UserDTO userToUserDTO(User user);

    PostDTO postToPostDTO(Post post);

    default UserDTO userToUserDTOWithPosts(User user, List<PostDTO> posts) {
        UserDTO userDTO = userToUserDTO(user);
        userDTO.setPosts(posts);
        return userDTO;
    }
}

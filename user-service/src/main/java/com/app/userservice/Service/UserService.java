package com.app.userservice.Service;

import com.app.userservice.DTO.UserRequest;
import com.app.userservice.DTO.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    String createUser(UserRequest userRequest);
    String updateUser(Long id, UserRequest userRequest);
    String deleteUser(Long id);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse getUserByUsername(String username);
    UserResponse getUserByEmail(String email);

    List<UserResponse> getUserFromSpecificGroup(Long groupId);

    void groupDeletedEvent(Long groupId);

    void userRemovedFromGroupEvent(Long userId, Long groupId);

    void userAddedToGroupEvent(Long userId, Long groupId);

}

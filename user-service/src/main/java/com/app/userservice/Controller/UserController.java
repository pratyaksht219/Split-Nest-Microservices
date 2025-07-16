package com.app.userservice.Controller;

import com.app.userservice.DTO.UserRequest;
import com.app.userservice.DTO.UserResponse;
import com.app.userservice.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/users")
    public ResponseEntity<String> createNewUser(@Valid @RequestBody UserRequest userRequest){
        String response = userService.createUser(userRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAlUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers();
        return ResponseEntity.ok(userResponseList);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@Valid @PathVariable Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@Valid @PathVariable String username) {
        UserResponse userResponse = userService.getUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@Valid @PathVariable String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUserById(@Valid @PathVariable String userId) {
        String response = userService.deleteUser(Long.parseLong(userId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/update-profile/{userId}")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UserRequest userRequest,@Valid @PathVariable Long userId) {
        String userResponse = userService.updateUser(userId, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/users/groups/{groupId}")
    public ResponseEntity<List<UserResponse>> getUsersFromASpecificGroup(@Valid @PathVariable Long groupId) {
        List<UserResponse> userResponseList = userService.getUserFromSpecificGroup(groupId);
        return ResponseEntity.ok(userResponseList);
    }
}


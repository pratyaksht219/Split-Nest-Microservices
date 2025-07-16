package com.app.userservice.Service;

import com.app.Kafka.Events.UserDeletedEvent;
import com.app.userservice.Clients.Kafka.Producer;
import com.app.userservice.Clients.WebClient.GroupClient;
import com.app.userservice.DTO.UserRequest;
import com.app.userservice.DTO.UserResponse;

import com.app.userservice.Entities.User;
import com.app.userservice.Repository.UserRepository;

import com.app.userservice.exceptions.APIException;
import com.app.userservice.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final Producer producer;

    private final GroupClient groupClient;


    @Override
    public String createUser(UserRequest userRequest) {
        try{
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setFullname(userRequest.getFullname());
            user.setEmail(userRequest.getEmail());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            userRepository.save(user);
            return "User Created Successfully"+user.toString();
        }catch (Exception e){
            throw new APIException("User Creation Failed");
        }
    }

    @Override
    public String updateUser(Long id, UserRequest userRequest) {
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
            user.setUsername(userRequest.getUsername());
            user.setFullname(userRequest.getFullname());
            user.setEmail(userRequest.getEmail());
            user.setPhoneNumber(userRequest.getPhoneNumber());


            userRepository.save(user);
            return "User Updated Successfully" + user.toString();
        }catch (Exception e){
            throw new APIException("User Update Failed");
        }
    }


    @Transactional
    @Override
    public String deleteUser(Long id) {
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));

            userRepository.delete(user);
            //---------------------------------
//            delete the users from the groups, if any
//            delete the user from the expenses
            producer.sendUserDeletedEvent(new UserDeletedEvent(id));
            //---------------------------------

            return "User Deleted Successfully"+user.toString();
        }catch (Exception e){
            throw new APIException("User Deletion Failed");
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {
        try{
            List<User> allUsers = userRepository.findAll();
            if(allUsers.isEmpty()){
                throw new APIException("No Users Found");
            }
            return allUsers.stream()
                    .map(user->modelMapper.map(user, UserResponse.class))
                    .toList();
        }catch (Exception e){
            throw new APIException("User Retrieval Failed"+e.getMessage());
        }
    }

    @Override
    public UserResponse getUserById(Long id) {
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

            return modelMapper.map(user, UserResponse.class);
        }catch(Exception e){
            throw new APIException("User Retrieval Failed"+e.getMessage());
        }
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        try{
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", username));

            return modelMapper.map(user, UserResponse.class);
        }catch(Exception e){
            throw new APIException("User Retrieval Failed"+e.getMessage());
        }
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        try{
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", email));

            return modelMapper.map(user, UserResponse.class);
        }catch(Exception e){
            throw new APIException("User Retrieval Failed"+e.getMessage());
        }
    }

    @Override
    public List<UserResponse> getUserFromSpecificGroup(Long groupId) {
        if(groupClient.getGroupById(groupId)==null){
            throw new ResourceNotFoundException("Group", "id", groupId);
        }

        List<User> users = userRepository.findAllByGroupIdsContaining(groupId);
        if(users.isEmpty()){
            throw new ResourceNotFoundException("Users", "id", groupId);
        }

        return users.stream()
                .map(user->modelMapper.map(user, UserResponse.class))
                .toList();

    }

    //Kafka triggered/consumer Events.
    @Transactional
    @Override
    public void groupDeletedEvent(Long groupId) {
//        if(groupClient.getGroupById(groupId)==null){
//            throw new ResourceNotFoundException("Group", "id", groupId);
//        }

        List<User> users = userRepository.findAllByGroupIdsContaining(groupId);
        if(users.isEmpty()){
            throw new ResourceNotFoundException("Users", "id", groupId);
        }

        users.forEach(user->{
            user.getGroupIds().remove(groupId);
            userRepository.save(user);
        });
    }
    @Transactional
    @Override
    public void userRemovedFromGroupEvent(Long userId, Long groupId) {
        if(groupClient.getGroupById(groupId)==null){
            throw new ResourceNotFoundException("Group", "id", groupId);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.getGroupIds().remove(groupId);
        userRepository.save(user);
    }
    @Transactional
    @Override
    public void userAddedToGroupEvent(Long userId, Long groupId) {
        if(groupClient.getGroupById(groupId)==null){
            throw new ResourceNotFoundException("Group", "id", groupId);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.getGroupIds().add(groupId);
        userRepository.save(user);
    }



}
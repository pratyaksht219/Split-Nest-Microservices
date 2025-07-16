package com.app.groupservice.Service;



import com.app.Kafka.Events.GroupDeleteEvent;
import com.app.Kafka.Events.UserAddedToGroupEvent;
import com.app.Kafka.Events.UserRemovedFromGroupEvent;
import com.app.groupservice.Clients.WebClient.Kafka.Producer;
import com.app.groupservice.Clients.WebClient.UserClient;
import com.app.groupservice.DTO.GroupRequest;
import com.app.groupservice.DTO.GroupResponse;
import com.app.groupservice.Entity.Group;
import com.app.groupservice.Repository.GroupRepository;
import com.app.groupservice.exceptions.APIException;
import com.app.groupservice.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImplementation implements GroupService{
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private Producer producer;


    @Override
    public String createGroup(GroupRequest groupRequest) {
        try{
            Group group = new Group();
            group.setName(groupRequest.getName());
            group.setDescription(groupRequest.getDescription());
            groupRepository.save(group);
            return "Group Created Successfully";
        }catch (Exception e){
            throw new APIException("Error while creating group"+" "+e.getMessage());
        }
    }

    @Override
    public GroupResponse getGroupById(Long groupId) {
        try{
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new APIException("Group not found"));
            return modelMapper.map(group, GroupResponse.class);
        }catch(Exception e){
            throw new APIException("Error while fetching group"+" "+e.getMessage());
        }
    }

    @Override
    public List<GroupResponse> getAllGroups() {
        try{
            List<Group> groups = groupRepository.findAll();
            if(groups.isEmpty()){
                throw new APIException("No groups found");
            }
            return groups.stream()
                    .map(group->modelMapper.map(group, GroupResponse.class))
                    .toList();

        }catch (Exception e){
            throw new APIException("Error while fetching groups"+" "+e.getMessage());
        }
    }

    @Transactional
    @Override
    public String addUserToGroup(Long groupId, Long userId) {
        try{
            if (userClient.getUserById(userId) == null) {
                throw new ResourceNotFoundException("User", "id", userId);
            }
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new ResourceNotFoundException("group", "id", groupId));

            group.getMemberUserIds().add(userId);
            groupRepository.save(group);
            producer.sendUserAddedToGroupEvent(new UserAddedToGroupEvent(userId, groupId));
            return "User added to group successfully";
        }catch (Exception e){
            throw new APIException("Error while adding user to group"+" "+e.getMessage());
        }
    }

    @Override
    public String updateGroupDetails(Long groupId, GroupRequest groupRequest) {
        try{
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new ResourceNotFoundException("group", "id", groupId));
            group.setName(groupRequest.getName());
            group.setDescription(groupRequest.getDescription());
            groupRepository.save(group);
            return "Group Updated Successfully";
        }catch (Exception e){
            throw new APIException("Error while updating group"+" "+e.getMessage());
        }
    }
    @Transactional
    @Override
    public String deleteGroup(Long groupId) {
        try{
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new ResourceNotFoundException("group", "id", groupId));


            groupRepository.delete(group);

            if(!group.getMemberUserIds().isEmpty()){
                producer.sendGroupDeleteEvent(new GroupDeleteEvent(groupId));
                //consume in
                // user service,
                // expense service,
                // split service
            }
            return "Group Deleted Successfully";
        }catch(Exception e){
            throw new APIException("Error while deleting group"+" "+e.getMessage());
        }
    }

    @Transactional
    @Override
    public String removeUserFromGroup(Long groupId, Long userId) {
         try{
             if(userClient.getUserById(userId)==null){
                 throw new ResourceNotFoundException("User", "id", userId);
             }
             Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new ResourceNotFoundException("group", "id", groupId));
             group.getMemberUserIds().remove(userId);


             groupRepository.save(group);
             producer.sendUserRemovedFromGroupEvent(new UserRemovedFromGroupEvent(userId, groupId));
             // consumed in user service,
             // expense service,
             // split service

             return "User removed from group successfully";

         }catch (Exception e){
             throw new APIException("Error while removing user from group"+" "+e.getMessage());
         }
    }
    @Override
    public List<GroupResponse> getAllGroupsByUserId(Long userId) {
        if(userClient.getUserById(userId)==null){
            throw new ResourceNotFoundException("User", "id", userId);
        }
        List<Group> groups = groupRepository.findAllByMemberUserIdsContaining(userId);
        if(groups.isEmpty()){
            throw new ResourceNotFoundException("Groups", "userId", userId);
        }
        return groups.stream()
                .map(group->modelMapper.map(group, GroupResponse.class))
                .toList();
    }

    @Transactional
    @Override
    public void removedUserEvent(Long userId) {
        try{
            List<Group> groups = groupRepository.findAllByMemberUserIdsContaining(userId);
            if(groups.isEmpty()){
                return;
            }
            for(Group group:groups){
                group.getMemberUserIds().remove(userId);
                groupRepository.save(group);
            }
        }catch (Exception e){
            throw new APIException("Error while removing user from group"+" "+e.getMessage());
        }
    }
}

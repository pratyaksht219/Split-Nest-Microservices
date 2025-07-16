package com.app.groupservice.Service;

import com.app.groupservice.DTO.GroupRequest;
import com.app.groupservice.DTO.GroupResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    String createGroup(GroupRequest groupRequest);

    String addUserToGroup(Long groupId, Long userId);
    //trigger userAddedToGroupEvent
    String updateGroupDetails(Long groupId , GroupRequest groupRequest);

    String deleteGroup(Long groupId);
    //triggerGroupDeletionEvent
    String removeUserFromGroup(Long groupId, Long userId);
    //trigger userRemovedFromGroupEvent


    //all get requests

    List<GroupResponse> getAllGroupsByUserId(Long userId);

    List<GroupResponse> getAllGroups();

    GroupResponse getGroupById(Long groupId);

    void removedUserEvent(Long userId);
}

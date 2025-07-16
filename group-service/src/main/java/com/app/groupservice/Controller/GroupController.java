package com.app.groupservice.Controller;

import com.app.groupservice.DTO.GroupRequest;
import com.app.groupservice.DTO.GroupResponse;
import com.app.groupservice.Service.GroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @GetMapping("/groups/{groupId}")
    public ResponseEntity<GroupResponse> getGroupById(@Valid @PathVariable Long groupId) {
        GroupResponse groupResponse = groupService.getGroupById(groupId);
        return ResponseEntity.ok(groupResponse);
    }


    @GetMapping("/groups")
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        List<GroupResponse> groupResponse = groupService.getAllGroups();
        return ResponseEntity.ok(groupResponse);

    }
    @GetMapping("/groups/users/{userId}")
    public ResponseEntity<List<GroupResponse>> getAllGroupsByUserId(@Valid@PathVariable Long userId){
        List<GroupResponse> groupResponse = groupService.getAllGroupsByUserId(userId);
        return ResponseEntity.ok(groupResponse);
    }

    @PostMapping("/groups")
    public ResponseEntity<String> createNewGroup(@Valid @RequestBody GroupRequest groupRequest){
        String response = groupService.createGroup(groupRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/groups/{groupId}/users/{userId}")
    public ResponseEntity<String> addUserToGroup(@Valid @PathVariable Long groupId, @Valid @PathVariable Long userId) throws JsonProcessingException {
        String response = groupService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<String> deleteGroup(@Valid @PathVariable String groupId) {
        String response = groupService.deleteGroup(Long.parseLong(groupId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/groups/{groupId}/users/{userId}")
    public ResponseEntity<String> deleteUserFromGroup(@Valid @PathVariable Long groupId, @Valid @PathVariable Long userId) {
        String response = groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/groups/{groupId}")
    public ResponseEntity<String> updateGroupDetails(@Valid @PathVariable String groupId, @Valid @RequestBody GroupRequest groupRequest) {
        String response = groupService.updateGroupDetails(Long.parseLong(groupId), groupRequest);
        return ResponseEntity.ok(response);

    }

}

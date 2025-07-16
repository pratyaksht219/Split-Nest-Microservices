package com.app.groupservice.DTO;

import com.app.groupservice.Entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String phoneNumber;
    private List<Group> groups;
}

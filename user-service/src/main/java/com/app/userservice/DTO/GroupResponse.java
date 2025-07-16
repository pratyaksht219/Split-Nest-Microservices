package com.app.userservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {
    private Long id;
    private String name;
    private String description;
    private List<Long> groupMembers;
    private LocalDateTime createdDateAndTime;
    private LocalDateTime modifiedDateAndTime;

}

package com.app.groupservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {
    private Long id;
    private String name;
    private String description;
    private List<Long> memberUserIds;
    private LocalDateTime createdDateAndTime;
    private LocalDateTime modifiedDateAndTime;
}

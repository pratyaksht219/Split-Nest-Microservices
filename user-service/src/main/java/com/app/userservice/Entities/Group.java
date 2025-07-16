package com.app.userservice.Entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class Group {
//    @Id
    private Long id;
    private String name;
    private String description;
    private String createdByUserId;

    @CreationTimestamp
    private LocalDateTime createdDateAndTime;
    @UpdateTimestamp
    private LocalDateTime modifiedDateAndTime;

    private List<Long> groupMemberIds;

}

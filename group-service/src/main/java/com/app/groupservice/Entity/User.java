package com.app.groupservice.Entity;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String phoneNumber;

    @ElementCollection
    private List<Long> groupIds;
}

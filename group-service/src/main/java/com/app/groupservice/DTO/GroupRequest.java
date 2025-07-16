package com.app.groupservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
//    private String createdByUserId;

}

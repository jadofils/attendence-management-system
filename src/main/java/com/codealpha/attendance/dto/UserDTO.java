package com.codealpha.attendance.dto;

import com.codealpha.attendance.model.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private UserRole role;
    private String studentProfile;
}

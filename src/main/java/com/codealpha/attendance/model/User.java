package com.codealpha.attendance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {  // Implement Serializable

    private static final long serialVersionUID = 1L;  // Recommended for Serializable classes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = true)
    @Pattern(
        regexp = ".*\\.(jpeg|png|jpg|svg|tiff)$",
        message = "Profile image must be a valid file type (jpeg, png, jpg, svg, tiff)"
    )
    private String studentProfile;
}

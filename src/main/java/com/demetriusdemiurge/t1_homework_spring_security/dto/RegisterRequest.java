package com.demetriusdemiurge.t1_homework_spring_security.dto;

import com.demetriusdemiurge.t1_homework_spring_security.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String login;
    private String email;
    private String password;
    private Set<Role> roles;
}
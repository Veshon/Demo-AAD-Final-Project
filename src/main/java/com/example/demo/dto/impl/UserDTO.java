package com.example.demo.dto.impl;

import com.example.demo.entity.Role;
import com.example.demo.dto.UserStatus;
import com.example.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements UserStatus {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profilePic;
    private Role role;
}


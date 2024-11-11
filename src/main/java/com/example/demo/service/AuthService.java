package com.example.demo.service;

import com.example.demo.dto.impl.UserDTO;
import com.example.demo.secure.JWTAuthResponse;
import com.example.demo.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDTO userDTO);
    JWTAuthResponse refreshToken(String accessToken);
}

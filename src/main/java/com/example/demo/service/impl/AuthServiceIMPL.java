package com.example.demo.service.impl;

import com.example.demo.dao.UserDAO;
import com.example.demo.dto.impl.UserDTO;
import com.example.demo.entity.impl.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.secure.JWTAuthResponse;
import com.example.demo.secure.SignIn;
import com.example.demo.service.AuthService;
import com.example.demo.service.JWTService;
import com.example.demo.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthServiceIMPL implements AuthService {

    private final UserDAO userDAO;
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var user = userDAO.findByEmail(signIn.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        var generatedToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
        UserEntity savedUser = userDAO.save(mapping.toUserEntity(userDTO));
        var generatedToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        var userName = jwtService.extractUserName(accessToken);
        var findUser = userDAO.findByEmail(userName)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        var refreshToken = jwtService.refreshToken(findUser);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}

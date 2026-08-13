package com.project.fitness.controller;

import com.project.fitness.Security.JwtUtils;
import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.LoginResponse;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.ResponseUser;
import com.project.fitness.model.User;
import com.project.fitness.repository.UserRepository;
import com.project.fitness.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor       // generate param constructor only for final object
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<ResponseUser> register(@Valid @RequestBody RegisterRequest registerUser){
        return userService.create(registerUser);
    }

    // Make service layer of this end point
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> signing(@Valid @RequestBody LoginRequest loginRequest){
        try{
            User user = userService.signing(loginRequest);
            if(user == null){
                return ResponseEntity.status(401).build();
            }
            String token =  jwtUtils.generateJwtWithId(user.getId(), user.getRole().name());

            return ResponseEntity.ok(new LoginResponse(token));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }
}

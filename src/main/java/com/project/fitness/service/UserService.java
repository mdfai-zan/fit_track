package com.project.fitness.service;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.ResponseUser;
import com.project.fitness.model.Role;
import com.project.fitness.model.User;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // generate param constructor only for final object
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseUser> create(RegisterRequest registerRequest){
        Role role = registerRequest.getRole() != null ? registerRequest.getRole() : Role.USER;
        try {
            User user = User
                    .builder()
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(role)
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .build();

            User savedUser = userRepository.save(user);
            return ResponseEntity.ok( mapToResponse(savedUser) );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseUser mapToResponse(User savedUser) {
        ResponseUser responseUser = new ResponseUser();
        responseUser.setId(savedUser.getId());
        responseUser.setEmail(savedUser.getEmail());
        responseUser.setFirstName(savedUser.getFirstName());
        responseUser.setLastName(savedUser.getLastName());

        return responseUser;
    }

    public User signing(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail());

        if(user == null) return null;

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }
        return user;
    }

}

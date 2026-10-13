package com.adarsh.MedicareAi.service;

import com.adarsh.MedicareAi.dto.LoginRequest;
import com.adarsh.MedicareAi.dto.LoginResponse;
import com.adarsh.MedicareAi.dto.RegisterRequest;
import com.adarsh.MedicareAi.dto.RegisterResponse;
import com.adarsh.MedicareAi.entity.Role;
import com.adarsh.MedicareAi.entity.User;
import com.adarsh.MedicareAi.exception.AdminRegistrationException;
import com.adarsh.MedicareAi.exception.EmailAlreadyExistsException;
import com.adarsh.MedicareAi.exception.EmailNotFoundException;
import com.adarsh.MedicareAi.exception.InvalidPasswordException;
import com.adarsh.MedicareAi.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    // Due to using req args constructor no need of Autowired
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest request){
        // Validate role ->// check email -> // encrypt password -> // create User -> // save User

        if (request.getRole() == Role.ADMIN) {
            throw new AdminRegistrationException("Admin registration is not allowed");
        }

        boolean emailFound = userRepo.existsByEmail(request.getEmail());

        if (emailFound) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        userRepo.save(user);
        return new RegisterResponse(true, "Registration successful");
    }

    public LoginResponse login(LoginRequest request){

        Optional<User> user=userRepo.findByEmail(request.getEmail());

        if(user.isEmpty()){
            throw new EmailNotFoundException("Email is not registered");
        }
        User existingUser = user.get();
        String pass = existingUser.getPassword();

        boolean passwordMatch = passwordEncoder.matches(request.getPassword(), pass);

        if(!passwordMatch){
            throw new InvalidPasswordException("Invalid password");
        }

        // JWT will come here

        String token=jwtService.generateToken(existingUser);

//        LoginResponse response = new LoginResponse();  // This is also correct
//        response.setSuccess(true);
//        response.setMessage("Login successful");
//        response.setToken(token);

        return new LoginResponse(
                true,
                "Login successful",
                token
        );
    }
}

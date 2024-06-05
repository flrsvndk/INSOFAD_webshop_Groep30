package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.config.JWTUtil;
import com.example.todoappdeel3.dao.AdressDAO;
import com.example.todoappdeel3.dao.AuthenticationDAO;
import com.example.todoappdeel3.dao.UserRepository;
import com.example.todoappdeel3.dto.AuthenticationDTO;
import com.example.todoappdeel3.dto.CustomUserDTO;
import com.example.todoappdeel3.dto.LoginResponse;
import com.example.todoappdeel3.dto.RoleUpgradeDTO;
import com.example.todoappdeel3.models.Adress;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.services.CredentialValidator;
import com.example.todoappdeel3.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userDAO;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final CredentialValidator validator;
    private final AuthenticationDAO authenticationDAO;

    public AuthController(UserRepository userDAO, JWTUtil jwtUtil, AuthenticationManager authManager, PasswordEncoder passwordEncoder, CredentialValidator validator, AuthenticationDAO authenticationDAO) {
        this.userDAO = userDAO;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.authenticationDAO = authenticationDAO;
    }

    @GetMapping("/userRoles")
    public ResponseEntity<List<CustomUser>> getStaffAndAdministrators(){
        return ResponseEntity.ok(this.authenticationDAO.getStaff());
    }

    @PostMapping("/admin/new/role")
    public ResponseEntity<String> addNewRole(@RequestBody RoleUpgradeDTO roleUpgradeDTO) {
        String message = this.authenticationDAO.newRole(roleUpgradeDTO);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody AuthenticationDTO authenticationDTO) {
        if (!validator.isValidEmail(authenticationDTO.email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid email provided"
            );
        }

        if (!validator.isValidPassword(authenticationDTO.password)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid password provided"
            );
        }

        CustomUser customUser = userDAO.findByEmail(authenticationDTO.email);

        if (customUser != null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Can not register with this email"
            );
        }
        String encodedPassword = passwordEncoder.encode(authenticationDTO.password);

        CustomUser registeredCustomUser = new CustomUser(authenticationDTO.name, authenticationDTO.infix, authenticationDTO.lastName, authenticationDTO.email, encodedPassword);
        userDAO.save(registeredCustomUser);
        String token = jwtUtil.generateToken(registeredCustomUser.getEmail());
        LoginResponse loginResponse = new LoginResponse(registeredCustomUser.getEmail(), token);
        return ResponseEntity.ok(loginResponse);
    }




    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationDTO body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.email, body.password);

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.email);

            CustomUser customUser = userDAO.findByEmail(body.email);
            LoginResponse loginResponse = new LoginResponse(customUser.getEmail(), token);


            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException authExc) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No valid credentials"
            );
        }
    }

    @GetMapping("/user")
    public ResponseEntity<CustomUser> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        CustomUser customUser = userDAO.findByEmail(userEmail);
        return ResponseEntity.ok(customUser);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<CustomUser>> getAllUsers() {
        return ResponseEntity.ok(userDAO.findAll());
    }
    

    @GetMapping("/user/role")
    public ResponseEntity<String> getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        CustomUser customUser = userDAO.findByEmail(userEmail);
        return ResponseEntity.ok(customUser.getRole());
    }


    @PutMapping("/user")
    public ResponseEntity<LoginResponse> updateUser(@RequestBody CustomUserDTO updatedUser) {
        CustomUser savedUser = this.authenticationDAO.updatedUser(updatedUser);
        String token = jwtUtil.generateToken(savedUser.getEmail());
        LoginResponse loginResponse = new LoginResponse(savedUser.getEmail(), token);
        return ResponseEntity.ok(loginResponse);
    }


    @DeleteMapping("/user/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        CustomUser existingUser = userDAO.findByEmail(email);
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gebruiker niet gevonden");
        }

        userDAO.delete(existingUser);
        return ResponseEntity.noContent().build();
    }
}

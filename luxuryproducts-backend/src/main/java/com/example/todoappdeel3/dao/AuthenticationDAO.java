package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.CustomUserDTO;
import com.example.todoappdeel3.dto.RoleUpgradeDTO;
import com.example.todoappdeel3.models.Adress;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.repositories.AdressRepository;
import com.example.todoappdeel3.repositories.UserRepository;
import com.example.todoappdeel3.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationDAO {
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdressDAO adressDAO;
    private final AdressRepository adressRepository;

    public AuthenticationDAO(UserRepository userRepository, UserService userService, AdressDAO adressDAO, AdressRepository adressRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.adressDAO = adressDAO;
        this.adressRepository = adressRepository;
    }

    public List<CustomUser> getStaff(){
        Optional<List<CustomUser>> staffUsers = this.userRepository.findAllByRoleIsNot("USER");
        if(staffUsers.isPresent()){
            return staffUsers.get();
        } throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Geen gebruikers met een andere rol gevonden");
    }

    public CustomUser updatedUser(CustomUserDTO updatedUser){
        CustomUser existingUser = this.userService.getUser();

        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gebruiker niet gevonden");
        }

        if (updatedUser.adress == null){
            existingUser.setName(updatedUser.name);
            existingUser.setInfix(updatedUser.infix);
            existingUser.setLastName(updatedUser.lastName);
            existingUser.setEmail(updatedUser.email);
            if(updatedUser.imgUrl != null){
                existingUser.setImgUrl(updatedUser.imgUrl);
                userRepository.save(existingUser);
            }
            return this.userRepository.save(existingUser);
        } else {
            updatedUser.adress.save = true;

            Adress updatedAdress = this.adressDAO.createAdress(updatedUser.adress);
            existingUser.setAdress(updatedAdress);

            if (updatedUser.imgUrl != null) {
                existingUser.setImgUrl(updatedUser.imgUrl);
                userRepository.save(existingUser);
            }
            existingUser.setName(updatedUser.name);
            existingUser.setInfix(updatedUser.infix);
            existingUser.setLastName(updatedUser.lastName);
            existingUser.setEmail(updatedUser.email);

            this.adressRepository.save(updatedAdress);
            return userRepository.save(existingUser);
        }
    }

    public String newRole(RoleUpgradeDTO roleUpgradeDTO){
        if (roleUpgradeDTO.newRole.equalsIgnoreCase("staff")){
            CustomUser user = this.userRepository.findByEmail(roleUpgradeDTO.email);
            user.setRole("STAFF");
            this.userRepository.save(user);
            return user.getEmail() + " is appointed Staff Status";
        } else if (roleUpgradeDTO.newRole.equalsIgnoreCase("admin")) {
            CustomUser user = this.userRepository.findByEmail(roleUpgradeDTO.email);
            user.setRole("ADMIN");
            this.userRepository.save(user);
            return user.getEmail() + " is appointed Admin Status";
        } else if (roleUpgradeDTO.newRole.equalsIgnoreCase("user")) {
            CustomUser user = this.userRepository.findByEmail(roleUpgradeDTO.email);
            user.setRole("USER");
            this.userRepository.save(user);
            return user.getEmail() + " is appointed User Status";
        }
        return roleUpgradeDTO.newRole + " was not an option for upgrade";
    }
}

package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.RoleUpgradeDTO;
import com.example.todoappdeel3.models.CustomUser;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDAO {
    private final UserRepository userRepository;

    public AuthenticationDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String newRole(RoleUpgradeDTO roleUpgradeDTO){
        if (roleUpgradeDTO.newRole.toLowerCase() == "staff"){
            CustomUser user = this.userRepository.findByEmail(roleUpgradeDTO.email);
            user.setRole("STAFF");
            this.userRepository.save(user);
            return user.getEmail() + " is appointed Staff Status";
        } else if (roleUpgradeDTO.newRole.toLowerCase() == "admin") {
            CustomUser user = this.userRepository.findByEmail(roleUpgradeDTO.email);
            user.setRole("ADMIN");
            this.userRepository.save(user);
            return user.getEmail() + " is appointed Admin Status";
        } else if (roleUpgradeDTO.newRole.toLowerCase() == "user") {
            CustomUser user = this.userRepository.findByEmail(roleUpgradeDTO.email);
            user.setRole("USER");
            this.userRepository.save(user);
            return user.getEmail() + " is appointed User Status";
        }
        return roleUpgradeDTO.newRole + " was not an option for upgrade";
    }
}

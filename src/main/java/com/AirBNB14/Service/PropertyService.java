package com.AirBNB14.Service;

import com.AirBNB14.DTO.LoginDto;
import com.AirBNB14.DTO.PropertyUserDto;
import com.AirBNB14.Repo.PropertyUserRepository;
import com.AirBNB14.entity.PropertyUser;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {
    private PropertyUserRepository propertyUserRepository;
    private JWTService jwtService;

    public PropertyService(PropertyUserRepository propertyUserRepository, JWTService jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService = jwtService;
    }

    public PropertyUser register(PropertyUserDto propertyUserDto){
        PropertyUser user=new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setEmail(propertyUserDto.getEmail());
        user.setRole(propertyUserDto.getRole());
        user.setUsername(propertyUserDto.getUsername());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));
        return propertyUserRepository.save(user);
    }

    public String login(LoginDto loginDto){
        Optional<PropertyUser> byUsername = propertyUserRepository.findByUsername(loginDto.getUsername());
        if(byUsername.isPresent()){
            PropertyUser propertyUser = byUsername.get();
            if (BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword())){
                String s = jwtService.generateToken(propertyUser);
                return s;

            }
        }
        return null;
    }
}

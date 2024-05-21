package com.AirBNB14.controller;

import com.AirBNB14.DTO.JWTResponseDto;
import com.AirBNB14.DTO.LoginDto;
import com.AirBNB14.DTO.PropertyUserDto;
import com.AirBNB14.Service.PropertyService;
import com.AirBNB14.entity.PropertyUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class PropertyUserController {
    private PropertyService propertyService;

    public PropertyUserController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PropertyUserDto propertyUserDto){
        PropertyUser register = propertyService.register(propertyUserDto);
        if (register!=null){
            return new ResponseEntity<>("regggggistration successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody  LoginDto loginDto){
        String login = propertyService.login(loginDto);
        if (login !=null){
            JWTResponseDto jwtResponseDto= new JWTResponseDto();
            jwtResponseDto.setToken(login);
            return new ResponseEntity<>(jwtResponseDto,HttpStatus.OK);
        }
        return new ResponseEntity<>("invalidCredential",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/profile")
    public PropertyUser pr(@AuthenticationPrincipal PropertyUser propertyUser){
        return propertyUser;
    }
}

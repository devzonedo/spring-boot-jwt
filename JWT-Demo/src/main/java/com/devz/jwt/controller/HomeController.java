/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.devz.jwt.controller;

import com.devz.jwt.model.JWTRequest;
import com.devz.jwt.model.JWTResponse;
import com.devz.jwt.service.UserService;
import com.devz.jwt.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



/**
 *
 * @author devzo
 */
@RestController
public class HomeController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JWTUtility jwtUtil;
    
    @GetMapping("/")
    public String home() {
        return "Welcome to devz";
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authRequest) throws Exception{
       
        try {
            authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
         
        } catch (Exception e) {
            throw new Exception("Invalid username and password",e);
        }
        
        //return jwt
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        
        String generateToken = jwtUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new JWTResponse(generateToken));
       
    }

  
}

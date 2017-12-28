package com.ramannada.springdemo.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.ramannada.springdemo.entity.Auth;
import com.ramannada.springdemo.entity.User;
import com.ramannada.springdemo.security.TokenUtils;
import com.ramannada.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController extends Serializers.Base {
    private String tokenHeader;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> createToken(@RequestBody Auth auth) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getUsername(),
                        auth.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails user = userService.loadUserByUsername(auth.getUsername());
        final String token = tokenUtils.generateToken((User) user);

        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/auth")
    public ResponseEntity<?> getUsername(@RequestParam("auth") String auth, HttpServletRequest request) {
        String username = tokenUtils.getUsernameFromToken(auth);

        UserDetails user = userService.loadUserByUsername(username);


        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return ResponseEntity.ok().body(token);
    }
}

package com.postgresql.hts.controller;

import com.postgresql.hts.io.ProfileRequest;
import com.postgresql.hts.io.ProfileResponse;
import com.postgresql.hts.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1.0") (in app.properties)
@RequiredArgsConstructor

public class ProfileController {
    @Autowired
    private final ProfileService profileService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request){
        ProfileResponse response = profileService.createProfile(request);
        //TODO:  send welcome email
        return response;
    }

    @GetMapping("/profile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email){
        return  profileService.getProfile(email);
    }

}

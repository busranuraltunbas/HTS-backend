package com.postgresql.hts.service;

import com.postgresql.hts.io.ProfileRequest;
import com.postgresql.hts.io.ProfileResponse;
import com.postgresql.hts.model.UserEntity;
import com.postgresql.hts.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ProfileServiceImp implements ProfileService{

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProfileResponse createProfile(ProfileRequest request){
        UserEntity newProfile = convertToUserEntity(request);
        if(!userRepo.existsByEmail(request.getEmail())){
            newProfile = userRepo.save(newProfile);
            return convertToProfileResponce(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Bu e-posta kullanılmış");
    }

    @Override
    public ProfileResponse getProfile(String email) {
        UserEntity existingUser =  userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: "+ email));

        return convertToProfileResponce(existingUser);
    }

    private ProfileResponse convertToProfileResponce(UserEntity newProfile) {
        return  ProfileResponse.builder()
                .name(newProfile.getUserName())
                .email(newProfile.getEmail())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }

    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .userName(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountVerified(false)
                .resetVerifyOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();


    }
}

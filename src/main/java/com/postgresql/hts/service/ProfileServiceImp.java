package com.postgresql.hts.service;

import com.postgresql.hts.io.ProfileRequest;
import com.postgresql.hts.io.ProfileResponse;
import com.postgresql.hts.model.Role;
import com.postgresql.hts.model.UserEntity;
import com.postgresql.hts.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProfileServiceImp implements ProfileService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bu e-posta kullanılmış"
            );
        }

        UserEntity newProfile = convertToUserEntity(request);
        newProfile = userRepo.save(newProfile);

        try {
            sendOtp(newProfile.getEmail());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Doğrulama e-postası gönderilemedi"
            );
        }

        return convertToProfileResponse(newProfile);
    }

    @Override
    public ProfileResponse getProfile(String email) {

        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        return convertToProfileResponse(existingUser);
    }

    @Override
    public void sendResetOtp(String email) {

        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        String otp = String.valueOf(
                ThreadLocalRandom.current()
                        .nextInt(100000, 1000000)
        );

        long expiryTime =
                System.currentTimeMillis() + (15 * 60 * 1000);

        existingUser.setResetOtp(otp);
        existingUser.setResetOtpExpireAt(expiryTime);

        userRepo.save(existingUser);

        try {
            emailService.sendResetOtpEmail(
                    existingUser.getEmail(),
                    otp
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to send reset email",
                    e
            );
        }
    }

    @Override
    public void resetPassword(
            String email,
            String otp,
            String newPassword
    ) {

        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        if (existingUser.getResetOtp() == null ||
                !existingUser.getResetOtp().equals(otp)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Geçersiz OTP"
            );
        }

        if (existingUser.getResetOtpExpireAt() == null ||
                existingUser.getResetOtpExpireAt() <
                        System.currentTimeMillis()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "OTP'nin süresi dolmuş"
            );
        }

        existingUser.setPassword(
                passwordEncoder.encode(newPassword)
        );

        existingUser.setResetOtp(null);
        existingUser.setResetOtpExpireAt(0L);

        userRepo.save(existingUser);
    }

    @Override
    public void sendOtp(String email) {

        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        if (Boolean.TRUE.equals(
                existingUser.getIsAccountVerified()
        )) {
            return;
        }

        String otp = String.valueOf(
                ThreadLocalRandom.current()
                        .nextInt(100000, 1000000)
        );

        long expiryTime =
                System.currentTimeMillis() + (15 * 60 * 1000);

        existingUser.setVerifyOtp(otp);
        existingUser.setVerifyOtpExpireAt(expiryTime);

        userRepo.save(existingUser);

        try {
            emailService.sendOtpEmail(
                    existingUser.getEmail(),
                    otp
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to send verification email",
                    e
            );
        }
    }

    @Override
    public void verifyOtp(String email, String otp) {

        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        if (existingUser.getVerifyOtp() == null ||
                !existingUser.getVerifyOtp().equals(otp)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Geçersiz OTP"
            );
        }

        if (existingUser.getVerifyOtpExpireAt() == null ||
                existingUser.getVerifyOtpExpireAt() <
                        System.currentTimeMillis()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "OTP'nin süresi dolmuş"
            );
        }

        existingUser.setIsAccountVerified(true);
        existingUser.setVerifyOtp(null);
        existingUser.setVerifyOtpExpireAt(0L);

        userRepo.save(existingUser);
    }

    private ProfileResponse convertToProfileResponse(
            UserEntity user
    ) {

        return ProfileResponse.builder()
                .name(user.getUserName())
                .email(user.getEmail())
                .userId(user.getUserId())
                .isAccountVerified(user.getIsAccountVerified())
                .build();
    }

    private UserEntity convertToUserEntity(
            ProfileRequest request
    ) {

        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .userName(request.getName())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.USER)
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }
}
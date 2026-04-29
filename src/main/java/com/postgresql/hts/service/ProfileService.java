package com.postgresql.hts.service;

import com.postgresql.hts.io.ProfileRequest;
import com.postgresql.hts.io.ProfileResponse;

public interface ProfileService {
     ProfileResponse createProfile(ProfileRequest request);

     ProfileResponse getProfile(String email);

     void sendResetOtp(String email);

     void resetPassword(String email, String otp, String newPassword);

     void sendOtp(String userId);

     void verifyOtp(String userId, String otp);

     String getLoggedInUserId(String email);

}

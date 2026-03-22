package com.postgresql.hts.service;

import com.postgresql.hts.io.ProfileRequest;
import com.postgresql.hts.io.ProfileResponse;

public interface ProfileService {
     ProfileResponse createProfile(ProfileRequest request);

     ProfileResponse getProfile(String email);
}

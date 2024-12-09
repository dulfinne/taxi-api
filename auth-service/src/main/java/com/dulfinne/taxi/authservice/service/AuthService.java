package com.dulfinne.taxi.authservice.service;

import com.dulfinne.taxi.authservice.dto.request.RegistrationRequest;

public interface AuthService {
  void createUser(RegistrationRequest request);

  void createAdmin(RegistrationRequest request);
}

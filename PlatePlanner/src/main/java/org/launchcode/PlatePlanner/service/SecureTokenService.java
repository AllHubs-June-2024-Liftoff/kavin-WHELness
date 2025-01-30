package org.launchcode.PlatePlanner.service;

import org.launchcode.PlatePlanner.model.SecureToken;


public interface SecureTokenService {
    SecureToken createToken();
    void saveSecureToken(SecureToken secureToken);

    SecureToken findByToken(String token);
    void removeToken(SecureToken token);
}


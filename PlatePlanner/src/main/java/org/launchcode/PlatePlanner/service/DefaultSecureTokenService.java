package org.launchcode.PlatePlanner.service;

import org.launchcode.PlatePlanner.model.SecureToken;
import org.launchcode.PlatePlanner.repository.SecureTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

import java.time.LocalDateTime;


@Service
public class DefaultSecureTokenService implements SecureTokenService {

    private static BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(12);

    @Value("2800")
    private int tokenValidityInSeconds;

    @Autowired
    private SecureTokenRepository secureTokenRepository;

    @Override
    public SecureToken createToken() {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()));
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpiredAt(LocalDateTime.now().plusSeconds(tokenValidityInSeconds));
        this.saveSecureToken(secureToken);
        return secureToken;
    }

    @Override
    public void saveSecureToken(SecureToken secureToken) {
        secureTokenRepository.save(secureToken);
    }

    @Override
    public SecureToken findByToken(String token) {
        return secureTokenRepository.findByToken(token);
    }

    @Override
    public void removeToken(SecureToken token) {
        secureTokenRepository.delete(token);
    }

}

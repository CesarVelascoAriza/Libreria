package com.cava.examples.seguridad.auth;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * Lista de tokens revocados (logout) en memoria, indexada por jti hasta su expiración.
 */
@Service
public class TokenRevocationService {

    private final Map<String, Instant> revoked = new ConcurrentHashMap<>();

    public void revoke(String jti, Instant expiresAt) {
        purgeExpired();
        revoked.put(jti, expiresAt);
    }

    public boolean isRevoked(String jti) {
        Instant expiresAt = revoked.get(jti);
        return expiresAt != null && expiresAt.isAfter(Instant.now());
    }

    private void purgeExpired() {
        Instant now = Instant.now();
        revoked.values().removeIf(expiresAt -> !expiresAt.isAfter(now));
    }
}

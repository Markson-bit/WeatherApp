package com.example.SpringAPI.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String secret;
    private final long expirationMs;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs
    ) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    public String generateToken(String subject) {
        try {
            long now = System.currentTimeMillis();
            long exp = now + expirationMs;

            Map<String, Object> header = new HashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");

            Map<String, Object> payload = new HashMap<>();
            payload.put("sub", subject);
            payload.put("iat", now / 1000);
            payload.put("exp", exp / 1000);

            String headerJson = objectMapper.writeValueAsString(header);
            String payloadJson = objectMapper.writeValueAsString(payload);

            String headerB64 = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
            String payloadB64 = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));

            String unsignedToken = headerB64 + "." + payloadB64;
            String signature = sign(unsignedToken);

            return unsignedToken + "." + signature;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to generate JWT", e);
        }
    }

    public String extractUsername(String token) {
        JsonNode node = parsePayload(token);
        if (node == null || !node.has("sub")) {
            return null;
        }
        return node.get("sub").asText();
    }

    public boolean isTokenValid(String token, String username) {
        String subject = extractUsername(token);
        return subject != null && subject.equals(username) && !isTokenExpired(token) && isSignatureValid(token);
    }

    private boolean isTokenExpired(String token) {
        JsonNode node = parsePayload(token);
        if (node == null || !node.has("exp")) {
            return true;
        }
        long expSeconds = node.get("exp").asLong();
        long nowSeconds = Instant.now().getEpochSecond();
        return nowSeconds > expSeconds;
    }

    private boolean isSignatureValid(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) return false;
        String unsigned = parts[0] + "." + parts[1];
        String expectedSig = sign(unsigned);
        return constantTimeEquals(expectedSig, parts[2]);
    }

    private JsonNode parsePayload(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null;
            }
            byte[] decoded = Base64.getUrlDecoder().decode(parts[1]);
            return objectMapper.readTree(decoded);
        } catch (Exception e) {
            return null;
        }
    }

    private String sign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(keySpec);
            byte[] signatureBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return base64UrlEncode(signatureBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to sign JWT", e);
        }
    }

    private String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}

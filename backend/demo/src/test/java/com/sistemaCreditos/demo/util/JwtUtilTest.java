package com.sistemaCreditos.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "clave-de-prueba-de-al-menos-32-caracteres");
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 60000L);
    }

    @Test
    void shouldExtractUsernameFromGeneratedToken() {
        String token = jwtUtil.generateToken("ana");

        assertEquals(Optional.of("ana"), jwtUtil.extractUsername(token));
    }

    @Test
    void shouldRejectTamperedToken() {
        String token = jwtUtil.generateToken("ana") + "x";

        assertTrue(jwtUtil.extractUsername(token).isEmpty());
    }

    @Test
    void shouldRejectExpiredToken() {
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", -1000L);
        String token = jwtUtil.generateToken("ana");

        assertTrue(jwtUtil.extractUsername(token).isEmpty());
    }
}

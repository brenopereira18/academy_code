package com.academycode.academycode.utils;

import com.academycode.academycode.modules.course.enums.Category;
import com.academycode.academycode.modules.course.enums.Status;
import com.academycode.academycode.modules.course.model.CourseModel;

import com.academycode.academycode.modules.user.enums.UserRole;
import com.academycode.academycode.modules.user.model.UserModel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.time.Duration;
import java.time.Instant;

public class TestUtils {

    public static String objectToJSON(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UserModel userModel, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant expirationDate = Instant.now().plus(Duration.ofMinutes(10));
            String token = JWT.create()
                .withIssuer("academy-code")
                .withSubject(userModel.getUsername())
                .withExpiresAt(expirationDate)
                .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public static UserModel createUser(String name, String username, String password, UserRole role) {
        var user = UserModel.builder()
            .name(name)
            .username(username)
            .password(password)
            .role(role != null ? role : UserRole.USER)
            .build();
        return user;
    }

    public static CourseModel createCourse(String name, Category category, Status status) {
        var course = CourseModel.builder()
            .name(name)
            .category(category)
            .status(status != null ? status : Status.ACTIVE)
            .build();
        return course;
    }
}

package com.academycode.academycode.userProfile.unit;

import com.academycode.academycode.provider.TokenProvider;
import com.academycode.academycode.modules.user.dto.AuthenticationUserDTO;
import com.academycode.academycode.modules.user.dto.LoginResponseDTO;
import com.academycode.academycode.modules.user.controller.AuthenticationUserController;
import com.academycode.academycode.modules.user.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceLoginTest {

    @InjectMocks
    private AuthenticationUserController authenticationUserController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @Test
    public void should_authenticate_the_user_if_you_are_registered() {
        AuthenticationUserDTO authenticateUser = new AuthenticationUserDTO("testUser", "testPassword");
        UserModel userModel = new UserModel();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userModel);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String fakeToken = "fake-jwt-token";
        when(tokenProvider.generateToken(userModel)).thenReturn(fakeToken);

        ResponseEntity<LoginResponseDTO> response = authenticationUserController.login(authenticateUser);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(fakeToken, response.getBody().token());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generateToken(userModel);
    }

    @Test
    public void should_make_an_exception_when_the_user_not_exists() {
        AuthenticationUserDTO authenticateUser = new AuthenticationUserDTO("testUser", "testPassword");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        ResponseEntity<LoginResponseDTO> response = authenticationUserController.login(authenticateUser);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody().token()).isNull();

        verify(authenticationManager).authenticate(any());
        verify(tokenProvider, never()).generateToken(any());
    }
}

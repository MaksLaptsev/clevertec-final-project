package ru.clevertec.banking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.banking.dto.request.AuthenticationRequest;
import ru.clevertec.banking.dto.request.RefreshTokenRequest;
import ru.clevertec.banking.dto.response.AuthenticationResponse;
import ru.clevertec.banking.service.AuthenticationService;
import ru.clevertec.banking.service.JwtTokenService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
//    private final JwtTokenService jwtTokenService;

    @PostMapping("/signing")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<AuthenticationResponse> registerOrAuthenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return authenticationService.registerOrAuthenticateAsync(request);
    }

    @PostMapping("/token-refreshing")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse refresh(
            @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        return authenticationService.refresh(refreshTokenRequest.getRefreshToken());
    }

    // Как я понял не оч гуд ходить в этот сервис проверять токен...
    // если решаем сюда не ходить - удалить коменты
//    @GetMapping("/token-validation")
//    @ResponseStatus(HttpStatus.OK)
//    public void validateToken(@RequestParam("token") String token) {
//       jwtTokenService.isTokenValid(token);
//    }
}

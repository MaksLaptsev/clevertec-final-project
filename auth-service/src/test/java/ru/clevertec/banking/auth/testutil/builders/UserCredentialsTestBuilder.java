package ru.clevertec.banking.auth.testutil.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.banking.auth.entity.Role;
import ru.clevertec.banking.auth.entity.UserCredentials;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsTestBuilder implements TestBuilder<UserCredentials> {

    private UUID id = UUID.fromString("1a72a05f-4b8f-43c5-a889-1ebc6d9dc729");
    private String email = "email@mail.ru";
    private String password = "qwerty";
    private Role role = Role.USER;
    private String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdGllcyI6WyJVU0VSIl0sInN1YiI6IjE2IiwiaWF0IjoxNzA0ODE2ODE1LCJleHAiOjE3MDU0MjE2MTV9.h3FWhuWOlqpCvHXDjjlsx4LH4CJvphkCjv2cRwVqhfI";

    @Override
    public UserCredentials build() {
        return new UserCredentials(id, email, password, role, refreshToken);
    }
}

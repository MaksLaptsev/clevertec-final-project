package ru.clevertec.banking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.banking.dto.UserCredentialsDto;
import ru.clevertec.banking.dto.UserMapper;
import ru.clevertec.banking.dto.response.SmallUserCredentialsReponse;
import ru.clevertec.banking.exception.UserNotFoundException;
import ru.clevertec.banking.exception.UserOperationException;
import ru.clevertec.banking.repository.UserCredentialsRepository;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserCredentialsRepository userRepository;
    private final UserMapper userMapper;

    // Опять же если мы не идем сюда проверять токен, удалить
//    public SmallUserCredentialsReponse getSmallUserFromContext() {
//        return Optional.of(userRepository.getUserFromContext())
//                       .map(userMapper::toSmallDto)
//                       .orElseThrow(() -> new UserNotFoundException("User not found"));
//    }

    public Optional<UserCredentialsDto> getOptionalByEmail(String email) {
        return userRepository.findByEmail(email)
                             .map(userMapper::toDto);
    }
    @Transactional
    public UserCredentialsDto save(UserCredentialsDto user) {
        return Stream.of(user)
                     .map(userMapper::toEntity)
                     .map(userRepository::save)
                     .map(userMapper::toDto)
                     .findFirst()
                     .orElseThrow(() -> new UserOperationException(
                             String.format("User with email = %s not saved", user.getEmail())));
    }
}

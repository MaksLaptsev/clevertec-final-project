package ru.clevertec.banking.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.clevertec.banking.dto.account.AccountRequest;
import ru.clevertec.banking.service.AccountService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountQueueConsumer {
    private final AccountService service;

    @RabbitListener(queues = "account_info")
    public void readMessageFromQueue(AccountRequest message) {
        Optional.of(message)
                .map(service::save);
    }
}

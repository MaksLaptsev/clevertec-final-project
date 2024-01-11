package ru.clevertec.banking.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.service.CardService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardQueueConsumer {
    private final CardService service;

    @RabbitListener(queues = "card-info")
    public void readMessageFromQueue(CardRequest message) {
        Optional.of(message)
                .map(service::save);
    }
}

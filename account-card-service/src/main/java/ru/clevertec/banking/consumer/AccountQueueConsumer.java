package ru.clevertec.banking.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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
    private final ObjectMapper mapper;

    @RabbitListener(queues = "account_info")
    public void readMessageFromQueue(Message message) {
        Optional.of(message)
                .map(Message::getBody)
                .map(String::new)
                .map(s -> {log.info(s); return s;})
                .map(s->{
                    try {
                        return mapper.readTree(s);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("uncorrect message: "+s);
                    }
                })
                .map(str -> mapper.convertValue(str,AccountRequest.class))
                .map(service::save);
    }

}

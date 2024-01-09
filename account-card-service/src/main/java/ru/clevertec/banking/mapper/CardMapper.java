package ru.clevertec.banking.mapper;

import org.mapstruct.*;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardRequestForUpdate;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.entity.Card;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {
    Card toCardSave(CardRequest request);

    Card toCardUpdate(CardRequestForUpdate request);

    CardResponse toResponse(Card card);

    List<CardResponse> toListOfResponse(List<Card> cards);

    @Mapping(target = "card.customerType", source = "request.customer_type",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "card.cardStatus", source = "request.card_status",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "card.iban", source = "request.iban",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Card updateFromRequest(CardRequestForUpdate request, @MappingTarget Card card);
}

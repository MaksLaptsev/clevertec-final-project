package ru.clevertec.banking.mapper;

import org.mapstruct.*;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardRequestForUpdate;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.entity.Card;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {

    @Mapping(target = "cardNumber",source = "request.card_number")
    @Mapping(target = "customerId",source = "request.customer_id")
    @Mapping(target = "customerType",source = "request.customer_type")
    @Mapping(target = "cardStatus",source = "request.card_status")
    Card fromRequest(CardRequest request);

    @Mapping(target = "card_number",source = "card.cardNumber")
    @Mapping(target = "customer_id",source = "card.customerId")
    @Mapping(target = "customer_type",source = "card.customerType")
    @Mapping(target = "card_status",source = "card.cardStatus")
    CardResponse toResponse(Card card);


    List<CardResponse> toListResponse(List<Card> cards);

    @Mapping(target = "card.customerType", source = "request.customer_type",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "card.cardStatus", source = "request.card_status",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "card.iban", source = "request.iban",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Card updateFromRequest(CardRequestForUpdate request, @MappingTarget Card card);
}

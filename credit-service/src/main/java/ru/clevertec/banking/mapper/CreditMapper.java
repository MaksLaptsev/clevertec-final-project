package ru.clevertec.banking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.banking.dto.CreditRequest;
import ru.clevertec.banking.dto.CreditRequestForUpdate;
import ru.clevertec.banking.dto.CreditResponse;
import ru.clevertec.banking.entity.Credit;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditMapper {

    @Mapping(target = "customer_id", source = "credit.customerId")
    @Mapping(target = "customer_type", source = "credit.customerType")
    CreditResponse toResponse(Credit credit);


    @Mapping(target = "customerId", source = "request.customer_id")
    @Mapping(target = "customerType", source = "request.customer_type")
    Credit fromRequest(CreditRequest request);


    @Mapping(target = "credit.repaymentDate", source = "request.repaymentDate",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "credit.rate", source = "request.rate",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "credit.possibleRepayment", source = "request.possibleRepayment",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "credit.isClosed", source = "request.isClosed",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customerType", source = "request.customer_type",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Credit updateFromRequest(CreditRequestForUpdate request, Credit credit);

    @Mapping(target = "customerId", source = "response.customer_id")
    @Mapping(target = "customerType", source = "response.customer_type")
    Credit fromResponse(CreditResponse response);
}

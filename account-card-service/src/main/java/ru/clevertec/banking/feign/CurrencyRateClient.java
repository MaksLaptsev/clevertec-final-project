package ru.clevertec.banking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("${clevertec.feign.service.currency-client}")
public interface CurrencyRateClient {
    @RequestMapping(method = RequestMethod.GET, value = "${clevertec.feign.service.currency-get-path}",
            consumes = "application/json")
    String getCurrency();
}

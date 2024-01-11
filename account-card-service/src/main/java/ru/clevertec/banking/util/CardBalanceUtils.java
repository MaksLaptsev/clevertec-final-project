package ru.clevertec.banking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import ru.clevertec.banking.dto.card.Balance;
import ru.clevertec.banking.entity.Card;
import ru.clevertec.banking.feign.CurrencyRateClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardBalanceUtils {
    private final ObjectMapper mapper;
    private final CurrencyRateClient currencyRateApi;

    private String currencyRate = """
            {
                        "startDt": "2024-01-03T13:56:51.604498616+03:00",
                        "exchangeRates": [
                            {
                                "buyRate": 3.33,
                                "sellRate": 3.43,
                                "srcCurr": "EUR",
                                "reqCurr": "BYN"
                            },
                            {
                                "buyRate": 3.05,
                                "sellRate": 3.15,
                                "srcCurr": "USD",
                                "reqCurr": "BYN"
                            },
                            {
                                "id": 5,
                                "buyRate": 1.075,
                                "sellRate": 1.1,
                                "srcCurr": "EUR",
                                "reqCurr": "USD"
                            }
                        ]
                }
                        
            """;

    public Balance getBalance(Card card) throws JsonProcessingException {
        //currencyRate = currencyRateApi.getCurrency();

        String mainCurrencyCard = getCurrencyCode(card.getAccount().getCurrencyCode());
        BigDecimal accountAmount = card.getAccount().getAmount();

        Map<String, String> args = getBalanceArgs(accountAmount, mainCurrencyCard);

        return new Balance(mainCurrencyCard, accountAmount.setScale(2, RoundingMode.DOWN).toString(), args);
    }

    private String getCurrencyCode(String curr) {
        if (StringUtils.isNumeric(curr)) {
            int currNumeric = Integer.parseInt(curr);
            return Currency.getAvailableCurrencies().stream()
                    .filter(currency -> currency.getNumericCode() == currNumeric)
                    .findAny()
                    .map(Currency::getCurrencyCode)
                    .orElseThrow(() -> new RuntimeException("Unknown currency type"));
        } else return curr;
    }

    @SneakyThrows
    private Map<String, String> getBalanceArgs(BigDecimal accountAmount, String mainCurrencyCard) {
        JsonNode node = mapper.readTree(currencyRate);
        List<JsonNode> currencyList = mapper.readValue(node.get("exchangeRates").toString(),
                TypeFactory.defaultInstance().constructCollectionType(List.class, JsonNode.class));

        Map<String, String> args = new HashMap<>();

        for (JsonNode nod : currencyList) {
            if (clean(nod.get("reqCurr")).equals(mainCurrencyCard)) {
                args.put(clean(nod.get("srcCurr")),
                        convert(accountAmount, clean(nod.get("sellRate")), true).toString());
            }
            if (clean(nod.get("srcCurr")).equals(mainCurrencyCard)) {
                args.put(clean(nod.get("reqCurr")),
                        convert(accountAmount, clean(nod.get("buyRate")), false).toString());
            }
        }
        return args;
    }

    private BigDecimal convert(BigDecimal amount, String curRateStr, boolean reqCur) {
        BigDecimal curRate = BigDecimal.valueOf(Double.parseDouble(curRateStr));

        return reqCur ? amount.divide(curRate, 2, RoundingMode.DOWN) : amount.multiply(curRate)
                .setScale(2, RoundingMode.DOWN);
    }

    private String clean(JsonNode node) {
        return node.toString().replace("\"", "");
    }
}

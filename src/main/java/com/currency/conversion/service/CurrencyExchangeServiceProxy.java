package com.currency.conversion.service;

import com.currency.conversion.domain.CurrencyConversion;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange-service", url="http://localhost:8000")
//@FeignClient(name="currency-exchange-service")
@FeignClient(name="zuul-service")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

    //@GetMapping("/currency-exchange/from/{from}/to/{to}")
    @GetMapping("currency-exchange-service/currency-exchange/from/{from}/to/{to}")

    public CurrencyConversion retrieveExchangeValue(@PathVariable String from , @PathVariable String to);

}

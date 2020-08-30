package com.currency.conversion.controller;

import com.currency.conversion.domain.CurrencyConversion;
import com.currency.conversion.service.CurrencyExchangeServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;


    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        Map<String,String> uriPathVal= new HashMap<>();
        uriPathVal.put("from",from);
        uriPathVal.put("to",to);
        ResponseEntity<CurrencyConversion>response = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/"+from+"/to/"+to,
                CurrencyConversion.class,
                uriPathVal);

        CurrencyConversion currencyConversion=response.getBody();

        return  new CurrencyConversion(currencyConversion.getId(),
                from,
                to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                        currencyConversion.getPort());

    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){


        CurrencyConversion currencyConversion=currencyExchangeServiceProxy.retrieveExchangeValue(from, to);

        return  new CurrencyConversion(currencyConversion.getId(),
                from,
                to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getPort());

    }
}



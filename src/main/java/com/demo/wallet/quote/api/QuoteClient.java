package com.demo.wallet.quote.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "quote", url = "${quote.url}")
public interface QuoteClient {

    @GetMapping
    QuoteWrapper findAll();

    @GetMapping("/{symbol}")
    Quote findByTicker(@PathVariable String symbol);

}

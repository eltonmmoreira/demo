package com.demo.wallet.quote.api;

import com.demo.wallet.exception.QuoteNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("quote")
public class QuoteApi {

    private final QuoteClient quoteClient;

    public QuoteApi(QuoteClient quoteClient) {
        this.quoteClient = quoteClient;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Cacheable(value = "quoteCache", key = "'quote'")
    public List<Quote> findAll() {
        try {
            log.info("Buscando cotação de todas as ações");
            return quoteClient.findAll().getAllQuote();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/{ticker}")
    @ResponseStatus(code = HttpStatus.OK)
    @Cacheable(value = "quoteTickerCache", key = "#ticker")
    public Quote findBySymbol(@NonNull @PathVariable String ticker) {
        try {
            log.info("Buscando cotação do ticker [" + ticker + "]");
            var quoteResponse= quoteClient.findByTicker(ticker);

            if (quoteResponse == null) {
                log.info("Nenhuma cotação encontrada para o ticker [" + ticker + "]");
                throw new QuoteNotFoundException(ticker);
            }
            return quoteResponse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}

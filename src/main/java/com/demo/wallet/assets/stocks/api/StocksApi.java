package com.demo.wallet.assets.stocks.api;

import com.demo.wallet.exception.StocksNotFoundException;
import com.demo.wallet.assets.stocks.Stocks;
import com.demo.wallet.assets.stocks.StocksService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("stocks")
public class StocksApi {

    private final StocksService stocksService;

    public StocksApi(StocksService stocksService) {
        this.stocksService = stocksService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<StocksTo> findAll() {
        var all = stocksService.findAll()
                .stream()
                .map(this::mapper)
                .collect(Collectors.toList());
        if (all.isEmpty()) {
            throw new StocksNotFoundException();
        }
        return all;
    }

    @GetMapping("/{ticker}")
    @ResponseStatus(code = HttpStatus.OK)
    public StocksTo findByTicker(@PathVariable @NonNull String ticker) {
        return Optional.of(stocksService.findByTicker(ticker))
                .map(this::mapper)
                .orElse(null);
    }

    private StocksTo mapper(Stocks stocks) {
        return StocksTo.builder()
                .ticker(stocks.getTicker())
                .quantity(stocks.getQuantity())
                .averagePrice(stocks.getAveragePrice())
                .totalPrice(stocks.getTotalPrice())
                .wallet(stocks.getWallet().getId())
                .build();
    }
}

package com.demo.wallet.assets.stocks;

import com.demo.wallet.assets.order.Order;
import com.demo.wallet.assets.order.operation.Operation;
import com.demo.wallet.assets.wallet.Wallet;
import lombok.extern.log4j.Log4j2;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Log4j2
public class StockFactory {

    private final Order order;
    private Stocks stocksSaved;
    private final String ticker;
    private final Wallet wallet;
    private final Map<Operation, Consumer<Stocks>> updateStocksByOperation = new HashMap<>();

    public static StockFactory of(StocksService stocksService, Order order) {
        return new StockFactory(stocksService, order);
    }

    private StockFactory(StocksService stocksService, Order order) {
        this.order = order;
        this.ticker = order.getTicker();
        this.wallet = order.getWallet();

        updateStocksByOperation.put(Operation.BUY, updateByBuy(this.order));
        updateStocksByOperation.put(Operation.SALE, updateBySale(this.order));
        getSavedStocks(stocksService, order);
    }

    private void getSavedStocks(StocksService stocksService, Order order) {
        try {
            stocksSaved = stocksService.findByTickerAndWalletId(
                    order.getTicker(), order.getWallet().getId()
            );
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
    }

    public Stocks create() {
        return Objects.nonNull(stocksSaved)
                ? updateStocks()
                : newStocks();
    }

    private Stocks newStocks() {
        return Stocks.builder()
                .ticker(order.getTicker())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .averagePrice(calculateAveragePrice(order.getQuantity(), order.getTotalPrice()))
                .wallet(order.getWallet())
                .build();
    }

    private Stocks updateStocks() {
        var stocksUpdate = mapperStocks();

        updateStocksByOperation.get(order.getOperation())
                .accept(stocksUpdate);

        return stocksUpdate;
    }

    private Consumer<Stocks> updateByBuy(Order order) {
        return stocks -> {
            stocks.setQuantity(stocks.getQuantity().add(order.getQuantity()));
            stocks.setTotalPrice(stocks.getTotalPrice().add(order.getTotalPrice()));
            stocks.setAveragePrice(
                    calculateAveragePrice(stocks.getQuantity(), stocks.getTotalPrice())
            );
        };
    }

    private Consumer<Stocks> updateBySale(Order order) {
        return stocks -> {
            stocks.setQuantity(stocks.getQuantity().subtract(order.getQuantity()));
            stocks.setTotalPrice(stocks.getQuantity().multiply(stocks.getAveragePrice()));
        };
    }

    private BigDecimal calculateAveragePrice(BigDecimal quantity, BigDecimal value) {
        return value.divide(quantity, RoundingMode.HALF_UP);
    }

    private Stocks mapperStocks() {
        return Stocks.builder()
                .id(stocksSaved.getId())
                .ticker(ticker)
                .quantity(stocksSaved.getQuantity())
                .totalPrice(stocksSaved.getTotalPrice())
                .averagePrice(stocksSaved.getAveragePrice())
                .wallet(wallet)
                .build();
    }
}

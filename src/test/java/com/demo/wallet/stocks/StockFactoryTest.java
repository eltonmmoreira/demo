package com.demo.wallet.stocks;

import com.demo.wallet.assets.order.Order;
import com.demo.wallet.assets.order.operation.Operation;
import com.demo.wallet.assets.stocks.Stocks;
import com.demo.wallet.assets.stocks.StocksService;
import com.demo.wallet.assets.wallet.Wallet;
import com.demo.wallet.assets.stocks.StockFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class StockFactoryTest {

    private static final String BBAS3 = "BBAS3";

    @Mock
    private StocksService stocksService;

    @Test
    public void createNewStocksBuyOperation() {
        var walletId = 1L;
        var quantity = BigDecimal.TEN;
        var price = BigDecimal.valueOf(30);

        var order =  Order.builder()
                .id(1L)
                .ticker(BBAS3)
                .operation(Operation.BUY)
                .quantity(quantity)
                .price(price)
                .totalPrice(quantity.multiply(price))
                .dateNegotiation(LocalDateTime.now())
                .wallet(new Wallet(walletId))
                .build();

        Mockito.when(stocksService.findByTickerAndWalletId(BBAS3, walletId))
                .thenReturn(null);

        var stocks = StockFactory.of(stocksService, order).create();

        assertThat(stocks.getId(), is(nullValue()));
        assertThat(stocks.getAveragePrice(), equalTo(BigDecimal.valueOf(30)));
    }

    @Test
    public void updateExistentStocksBuyOperation() {
        var walletId = 1L;
        var quantity = BigDecimal.TEN;
        var price = BigDecimal.valueOf(28);

        var order =  Order.builder()
                .id(1L)
                .ticker(BBAS3)
                .operation(Operation.BUY)
                .quantity(quantity)
                .price(price)
                .totalPrice(quantity.multiply(price))
                .dateNegotiation(LocalDateTime.now())
                .wallet(new Wallet(walletId))
                .build();

        var updateStocks = Stocks.builder()
                .id(1L)
                .ticker(BBAS3)
                .quantity(BigDecimal.valueOf(10))
                .totalPrice(BigDecimal.valueOf(300))
                .averagePrice(BigDecimal.valueOf(30))
                .wallet(new Wallet(walletId))
                .build();

        Mockito.when(stocksService.findByTickerAndWalletId(BBAS3, walletId))
                .thenReturn(updateStocks);

        var stocks = StockFactory.of(stocksService, order).create();

        assertThat(stocks.getId(), equalTo(updateStocks.getId()));
        assertThat(stocks.getQuantity(), equalTo(BigDecimal.valueOf(20)));
        assertThat(stocks.getTotalPrice(), equalTo(BigDecimal.valueOf(580)));
        assertThat(stocks.getAveragePrice(), equalTo(BigDecimal.valueOf(29)));
    }

    @Test
    public void saleOperationNotUpdateAveragePrice() {
        var walletId = 1L;
        var quantity = BigDecimal.valueOf(5);
        var price = BigDecimal.valueOf(35);

        var order =  Order.builder()
                .id(1L)
                .ticker(BBAS3)
                .operation(Operation.SALE)
                .quantity(quantity)
                .price(price)
                .totalPrice(quantity.multiply(price))
                .dateNegotiation(LocalDateTime.now())
                .wallet(new Wallet(walletId))
                .build();

        var updateStocks = Stocks.builder()
                .id(1L)
                .ticker(BBAS3)
                .quantity(BigDecimal.valueOf(20))
                .totalPrice(BigDecimal.valueOf(580))
                .averagePrice(BigDecimal.valueOf(29))
                .wallet(new Wallet(walletId))
                .build();

        Mockito.when(stocksService.findByTickerAndWalletId(BBAS3, walletId))
                .thenReturn(updateStocks);

        var stocks = StockFactory.of(stocksService, order).create();

        assertThat(stocks.getId(), equalTo(updateStocks.getId()));
        assertThat(stocks.getQuantity(), equalTo(BigDecimal.valueOf(15)));
        assertThat(stocks.getTotalPrice(), equalTo(BigDecimal.valueOf(435)));
        assertThat(stocks.getAveragePrice(), equalTo(BigDecimal.valueOf(29)));
    }

}

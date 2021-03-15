package com.demo.wallet.assets.stocks.listener;

import com.demo.wallet.assets.stocks.StocksService;
import com.demo.wallet.assets.order.event.PostCreateOrderEvent;
import com.demo.wallet.assets.stocks.StockFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PostCreateOrderListener {

    private final StocksService stocksService;

    public PostCreateOrderListener(StocksService stocksService) {
        this.stocksService = stocksService;
    }

    @EventListener
    public void handlePostCreateOrder(PostCreateOrderEvent event) {
        var order = event.getOrder();
        var stocks = StockFactory.of(stocksService, order).create();
        stocksService.save(stocks);
    }
}

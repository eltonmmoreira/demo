package com.demo.wallet.assets.stocks;

import java.util.List;

public interface StocksService {

    Stocks save(Stocks stocks);

    Stocks findByTicker(String ticker);

    List<Stocks> findAll();

    void delete(Long id);

    Stocks findByTickerAndWalletId(String ticker, Long walletId);
}

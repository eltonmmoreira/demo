package com.demo.wallet.assets.stocks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StocksRepository extends JpaRepository<Stocks, Long> {
    Optional<Stocks> findByTicker(String ticker);

    Optional<Stocks> findByTickerAndWalletId(String ticker, Long walletId);
}

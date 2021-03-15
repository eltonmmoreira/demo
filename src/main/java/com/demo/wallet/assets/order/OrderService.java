package com.demo.wallet.assets.order;

import com.demo.wallet.assets.wallet.Wallet;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order save(Order order);

    void delete(Long id);

    List<Order> findAll();

    List<Order> findByTicker(String ticker);

    Optional<Wallet> findByWalletId(Long id);
}

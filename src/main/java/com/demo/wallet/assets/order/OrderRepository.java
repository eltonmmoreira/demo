package com.demo.wallet.assets.order;

import com.demo.wallet.assets.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTicker(String ticker);

    Optional<Wallet> findByWalletId(Long id);
}

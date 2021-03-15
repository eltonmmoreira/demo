package com.demo.wallet.assets.order;

import com.demo.wallet.assets.order.event.PostCreateOrderEvent;
import com.demo.wallet.assets.wallet.Wallet;
import com.demo.wallet.assets.wallet.WalletRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final WalletRepository walletRepository;

    public OrderServiceImpl(OrderRepository ordersRepository, ApplicationEventPublisher applicationEventPublisher, WalletRepository walletRepository) {
        this.orderRepository = ordersRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.walletRepository = walletRepository;
    }

    @Override
    public Order save(Order order) {
        order = orderRepository.save(order);
        applicationEventPublisher.publishEvent(new PostCreateOrderEvent(this, order));
        return order;
    }

    @Override
    public void delete(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoResultException("Nenhum registro encontrado para exlusão");
        }
    }

    @Override
    public List<Order> findAll() {
        return Optional.of(orderRepository.findAll()).orElseThrow(() -> {
            throw new NoResultException("Nenhuma ordem encontrada");
        });
    }

    @Override
    public List<Order> findByTicker(String ticker) {
        return orderRepository.findByTicker(ticker);
    }

    @Override
    public Optional<Wallet> findByWalletId(Long id) {
        return orderRepository.findByWalletId(id);
    }
}

package com.demo.wallet.assets.order.api;

import com.demo.wallet.exception.WalletNotFoundException;
import com.demo.wallet.assets.order.Order;
import com.demo.wallet.assets.order.OrderService;
import com.demo.wallet.assets.wallet.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderApi {

    private final OrderService orderService;
    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public OrderApi(OrderService orderService, WalletRepository walletRepository) {
        this.orderService = orderService;
        this.walletRepository = walletRepository;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderTo create(@Valid @RequestBody @NonNull OrderTo orderTo) {
        var wallet = walletRepository.findById(orderTo.getWallet())
                .orElseThrow(() -> {
                    throw new WalletNotFoundException();
                });

        var stocks = modelMapper.map(orderTo, Order.class);
        stocks.setWallet(wallet);
        stocks = orderService.save(stocks);
        return mapper(stocks);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NonNull Long id) {
        orderService.delete(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<OrderTo> findAll() {
        return orderService.findAll()
                .stream()
                .map(this::mapper)
                .collect(Collectors.toList());
    }

    private OrderTo mapper(Order order) {
        return OrderTo.builder()
                .id(order.getId())
                .ticker(order.getTicker())
                .operation(order.getOperation())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .dateNegotiation(order.getDateNegotiation())
                .wallet(order.getWallet().getId())
                .build();
    }
}

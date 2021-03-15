package com.demo.wallet.assets.wallet.api;

import com.demo.wallet.exception.WalletNotFoundException;
import com.demo.wallet.assets.user.UserService;
import com.demo.wallet.assets.wallet.WalletRepository;
import com.demo.wallet.exception.UsedWalletException;
import com.demo.wallet.assets.order.OrderService;
import com.demo.wallet.assets.wallet.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wallet")
public class WalletApi {

    private final WalletRepository walletRepository;

    private final UserService userService;
    private final OrderService orderService;

    private final ModelMapper modelMapper = new ModelMapper();

    public WalletApi(WalletRepository walletRepository, UserService userService, OrderService orderService) {
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public WalletTo create(@Valid @RequestBody @NonNull WalletTo walletTo) {
        var user = userService.findByEmail(walletTo.getEmail());
        var wallet = modelMapper.map(walletTo, Wallet.class);
        wallet.setUser(user);

        wallet = walletRepository.save(wallet);

        walletTo = modelMapper.map(wallet, WalletTo.class);
        walletTo.setEmail(wallet.getUser().getEmail());
        return walletTo;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NonNull Long id) {
        Optional<Wallet> walletOptional = orderService.findByWalletId(id);
        if (walletOptional.isPresent()) {
            throw new UsedWalletException();
        }
        walletRepository.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<WalletTo> findAll() {
        return Optional.of(walletRepository.findAll()
                .stream()
                .map(s -> new WalletTo(s.getId(),
                        s.getDescription(),
                        s.getUser().getEmail())
                ).collect(Collectors.toList()))
                .orElseThrow(() -> {
                    throw new WalletNotFoundException();
                });
    }
}

package com.demo.wallet.assets.stocks;

import com.demo.wallet.exception.StocksNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
public class StocksServiceImpl implements StocksService {

    private final StocksRepository stocksRepository;

    public StocksServiceImpl(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public Stocks save(Stocks stocks) {
        return stocksRepository.save(stocks);
    }

    @Override
    public Stocks findByTicker(String ticker) {
        return stocksRepository.findByTicker(ticker).orElseThrow(() -> {
            throw new NoResultException(String.format("Nenhuma ação encontrada para o ticker [%s]", ticker));
        });
    }

    @Override
    public List<Stocks> findAll() {
        return Optional.of(stocksRepository.findAll())
                .orElseThrow(() -> {
                    throw new StocksNotFoundException();
                });
    }

    @Override
    public void delete(Long id) {
        stocksRepository.deleteById(id);
    }

    @Override
    public Stocks findByTickerAndWalletId(String ticker, Long walletId) {
       return stocksRepository.findByTickerAndWalletId(ticker, walletId)
               .orElseThrow(() -> {
                    throw new NoResultException(String.format("Ação não encontrada para o ticker %s e wallet %d", ticker, walletId));
               });
    }
}

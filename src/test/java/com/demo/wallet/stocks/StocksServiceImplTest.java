package com.demo.wallet.stocks;

import com.demo.wallet.assets.stocks.StocksRepository;
import com.demo.wallet.assets.stocks.StocksServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.NoResultException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StocksServiceImplTest {

    @InjectMocks
    private StocksServiceImpl stocksService;

    @Mock
    private StocksRepository stocksRepository;

    @Test
    public void findByTickerNotFound() {
        when(stocksRepository.findByTicker(any())).thenReturn(Optional.empty());
        assertThrows(NoResultException.class, () -> stocksService.findByTicker("BBAS3"));
    }

    @Test
    public void findByTickerAndWalletIdNotFound() {
        when(stocksRepository.findByTickerAndWalletId(any(), any())).thenReturn(Optional.empty());
        assertThrows(NoResultException.class, () -> stocksService.findByTickerAndWalletId("BBAS3", 1L));
    }
}

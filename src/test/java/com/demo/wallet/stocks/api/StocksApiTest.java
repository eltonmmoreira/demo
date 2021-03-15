package com.demo.wallet.stocks.api;

import com.demo.wallet.assets.stocks.Stocks;
import com.demo.wallet.assets.stocks.StocksServiceImpl;
import com.demo.wallet.assets.stocks.api.StocksApi;
import com.demo.wallet.assets.stocks.api.StocksTo;
import com.demo.wallet.assets.wallet.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StocksApiTest {

    private static final String URI_PATH = "/stocks";

    @InjectMocks
    private StocksApi stocksApi;

    @Mock
    private StocksServiceImpl stocksService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stocksApi).build();
    }

    @Test
    public void returnedOkStatusWhenCalledFindAll() throws Exception {
        final String BBAS3 = "BBAS3";
        final String ITSA4 = "ITSA4";
        var stocksList = List.of(
                Stocks.builder()
                        .ticker(BBAS3)
                        .wallet(new Wallet(1L))
                        .build(),
                Stocks.builder()
                        .ticker(ITSA4)
                        .wallet(new Wallet(1L))
                        .build());

        Mockito.when(stocksService.findAll()).thenReturn(stocksList);

        mockMvc.perform(get(URI_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker", is(equalTo(BBAS3))));
    }

    @Test
    public void returnedOkStatusWhenCalledFindByTickerValid() throws Exception {
        final String ticker = "ITSA4";

        var stocksTo = StocksTo.builder()
                .ticker(ticker)
                .build();
        var stocks = Stocks
                .builder()
                .ticker(ticker)
                .wallet(new Wallet(1L))
                .build();

        Mockito.when(stocksService.findByTicker(ticker))
                .thenReturn(stocks);

        mockMvc.perform(get(URI_PATH + "/" + stocksTo.getTicker())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker", is(equalTo(stocksTo.getTicker()))));
    }
}

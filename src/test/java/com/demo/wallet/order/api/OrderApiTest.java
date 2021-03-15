package com.demo.wallet.order.api;

import com.demo.wallet.utils.JsonConvertionUtils;
import com.demo.wallet.assets.order.Order;
import com.demo.wallet.assets.order.OrderServiceImpl;
import com.demo.wallet.assets.order.api.OrderApi;
import com.demo.wallet.assets.order.api.OrderTo;
import com.demo.wallet.assets.order.operation.Operation;
import com.demo.wallet.assets.wallet.Wallet;
import com.demo.wallet.assets.wallet.WalletRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderApiTest {

    private static final String URI_PATH = "/order";

    @InjectMocks
    private OrderApi orderApi;

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private WalletRepository walletRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderApi).build();
    }

    @Test
    public void returnedCreateStatusWhenPostIsCalled() throws Exception {
        final String BBAS3 = "BBAS3";
        var myWallet = 1L;
        var dateNegotiation = LocalDateTime.now();

        var orderTo = OrderTo.builder()
                .ticker(BBAS3)
                .operation(Operation.BUY)
                .quantity(BigDecimal.TEN)
                .price(BigDecimal.valueOf(30))
                .wallet(myWallet)
                .dateNegotiation(dateNegotiation)
                .build();

        var order = Order.builder()
                .id(1L)
                .ticker(BBAS3)
                .dateNegotiation(dateNegotiation)
                .quantity(BigDecimal.TEN)
                .price(BigDecimal.valueOf(30))
                .totalPrice(BigDecimal.valueOf(300))
                .wallet(new Wallet(myWallet))
                .build();

        Mockito.when(walletRepository.findById(myWallet)).thenReturn(Optional.of(new Wallet(myWallet)));
        Mockito.when(orderService.save(any())).thenReturn(order);

        mockMvc.perform(post(URI_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(orderTo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.ticker", is(equalTo(BBAS3))));
    }

}

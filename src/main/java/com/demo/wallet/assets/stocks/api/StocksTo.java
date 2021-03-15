package com.demo.wallet.assets.stocks.api;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StocksTo {
    private String ticker;
    private BigDecimal quantity;
    private BigDecimal averagePrice;
    private BigDecimal totalPrice;
    private Long wallet;
}

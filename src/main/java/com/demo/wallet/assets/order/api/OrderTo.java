package com.demo.wallet.assets.order.api;

import com.demo.wallet.assets.order.operation.Operation;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderTo {
    private Long id;

    @NotNull(message = "Ticker deve ser informado")
    private String ticker;

    @NotNull(message = "Operação deve ser informada")
    private Operation operation;

    @NotNull(message = "Quantidade deve ser informada")
    private BigDecimal quantity;

    @NotNull(message = "Valor deve ser informado")
    private BigDecimal price;

    private LocalDateTime dateNegotiation;

    @NotNull(message = "Carteira deve ser informada")
    private Long wallet;

    public BigDecimal getTotalPrice() {
        return quantity.multiply(price);
    }
}

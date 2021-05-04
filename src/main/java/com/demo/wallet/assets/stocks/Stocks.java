package com.demo.wallet.assets.stocks;

import com.demo.wallet.assets.wallet.Wallet;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"id"})
@Entity
@Table(name = "STOCKS", uniqueConstraints = {
        @UniqueConstraint(
                name = "UNQ_TICKER",
                columnNames = {"TICKER"}
        ),
        @UniqueConstraint(
                name = "UNQ_WALLET_ID",
                columnNames = {"ID_WALLET"}
        )
})
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TICKER", nullable = false, length = 10)
    private String ticker;

    @Column(name = "QUANTITY", nullable = false)
    private BigDecimal quantity;

    @Column(name = "AVERAGE_PRICE", nullable = false, precision = 15, scale = 2)
    private BigDecimal averagePrice;

    @Column(name = "TOTAL_PRICE", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_WALLET")
    private Wallet wallet;
}

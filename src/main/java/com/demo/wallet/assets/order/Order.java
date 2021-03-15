package com.demo.wallet.assets.order;

import com.demo.wallet.assets.order.operation.Operation;
import com.demo.wallet.assets.wallet.Wallet;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"id"})
@Entity
@Table(name = "ORDER_NEGOTIATION")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TICKER", nullable = false, length = 10)
    private String ticker;

    @Enumerated(EnumType.STRING)
    @Column(name = "OPERATION", nullable = false, length = 6)
    private Operation operation;

    @Column(name = "QUANTITY", nullable = false)
    private BigDecimal quantity;

    @Column(name = "PRICE", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "TOTAL_PRICE", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "DATE_NEGOTIATION", nullable = false)
    private LocalDateTime dateNegotiation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_WALLET")
    private Wallet wallet;

    @PrePersist
    private void prePersist() {
        if (dateNegotiation == null) {
            dateNegotiation = LocalDateTime.now();
        }
    }
 }

package com.demo.wallet.assets.wallet;

import com.demo.wallet.assets.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"id"})
@Entity
@Table(name = "WALLET")
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private User user;

    public Wallet(Long id) {
        this.id = id;
    }
}

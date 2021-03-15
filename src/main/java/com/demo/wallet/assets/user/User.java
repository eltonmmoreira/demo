package com.demo.wallet.assets.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER")
@ToString(of = {"email"})
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;



}

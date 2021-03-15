package com.demo.wallet.assets.wallet.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTo {
    private Long id;
    private String description;

    @NotNull(message = "E-mail não informado")
    @NotBlank(message = "E-mail não informado")
    @NotEmpty(message = "E-mail não informado")
    @Email
    private String email;
}

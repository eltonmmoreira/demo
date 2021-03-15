package com.demo.wallet.assets.user.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTo {
    private Long id;

    @Email
    @NotNull(message = "Email obrigatório")
    private String email;
}

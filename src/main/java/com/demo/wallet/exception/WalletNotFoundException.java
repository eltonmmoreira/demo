package com.demo.wallet.exception;

import javax.persistence.NoResultException;

public class WalletNotFoundException extends NoResultException {

    public WalletNotFoundException() {
        super("Nenhuma wallet encontrada");
    }
}

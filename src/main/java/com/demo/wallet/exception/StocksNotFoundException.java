package com.demo.wallet.exception;

import javax.persistence.NoResultException;

public class StocksNotFoundException extends NoResultException {

    public StocksNotFoundException() {
        super("Nenhuma ação encontrada");
    }

}

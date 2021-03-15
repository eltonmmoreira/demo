package com.demo.wallet.exception;

import javax.persistence.NoResultException;

public class QuoteNotFoundException extends NoResultException {

    public QuoteNotFoundException(String ticker) {
        super(String.format("Cotação não encontrada: Ticket[%s]", ticker));
    }
}

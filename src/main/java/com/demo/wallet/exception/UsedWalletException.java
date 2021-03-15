package com.demo.wallet.exception;

public class UsedWalletException extends RuntimeException {

    public UsedWalletException() {
        super("Carteira está sendo utilizada.");
    }
}

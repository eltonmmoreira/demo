package com.demo.wallet.exception;

public class RequiredWalletException extends Exception {

    public RequiredWalletException() {
        super("Wallet não informada");
    }
}

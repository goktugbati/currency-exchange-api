package com.currencystack.exception;

public class TransactionNotFoundException extends IllegalArgumentException {
    public TransactionNotFoundException(Long transactionId){
        super("No transaction found with ID: " + transactionId);
    }
}

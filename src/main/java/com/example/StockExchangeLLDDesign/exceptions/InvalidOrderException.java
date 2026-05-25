package com.example.StockExchangeLLDDesign.exceptions;

public class InvalidOrderException extends TradeException{

    public InvalidOrderException(String message){
        super("invalid"+message);
    }
}

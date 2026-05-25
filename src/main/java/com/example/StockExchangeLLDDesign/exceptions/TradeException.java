package com.example.StockExchangeLLDDesign.exceptions;

public class TradeException extends Exception {
    public TradeException(String message){
        super(message);
    }
    public TradeException(String message,Throwable cause){
        super(message,cause);
    }


}

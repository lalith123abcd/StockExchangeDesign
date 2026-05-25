package com.example.StockExchangeLLDDesign.exceptions;

public class OrderNotFoundException extends TradeException{

    public OrderNotFoundException(String orderId){
        super("order not found with id" + orderId);
    }
}

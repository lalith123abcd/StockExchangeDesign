package com.example.StockExchangeLLDDesign.exceptions;

import com.example.StockExchangeLLDDesign.models.User;

public class UserNotFoundException extends TradeException{

    public UserNotFoundException(String message){
        super(message);
    }
}

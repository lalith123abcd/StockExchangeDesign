package com.example.StockExchangeLLDDesign.services;

import com.example.StockExchangeLLDDesign.models.Trade;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TradeService {

    private final Map<String, Trade> tradeMap=new ConcurrentHashMap<>();



    public void save(Trade trade){
        String  tradeId=trade.getTradeId();
        if(!tradeMap.containsKey(tradeId)){
            tradeMap.put(tradeId,trade);
        }
    }

    public Optional<Trade> getTradeById(String tradeId){
        return Optional.ofNullable(tradeMap.get(tradeId));
    }

}

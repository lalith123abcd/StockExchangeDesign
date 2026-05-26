package com.example.StockExchangeLLDDesign.services;

import com.example.StockExchangeLLDDesign.models.Trade;

import java.util.Optional;

public interface ITradeService {

    void save(Trade trade);

    Optional<Trade> getTradeById(String tradeId);
}
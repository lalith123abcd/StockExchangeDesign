package com.example.StockExchangeLLDDesign.services;

import com.example.StockExchangeLLDDesign.dtos.OrderRequest;
import com.example.StockExchangeLLDDesign.models.Order;

import java.util.List;

public interface ITradingService {

    public Order placeOrder(OrderRequest orderRequest);
     void executeOrderMatch(Order newOrder);
    public List<Order> getOrderBooks(String symbol);
}

package com.example.StockExchangeLLDDesign.services.strategies;

import com.example.StockExchangeLLDDesign.models.Order;
import com.example.StockExchangeLLDDesign.models.Trade;

import java.util.List;

public interface OrderMatchingStrategy {

    String getStrategyName();

    List<Trade> matchOrder(Order newOrder, List<Order> existingOrders)
}

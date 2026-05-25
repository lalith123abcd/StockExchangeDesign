package com.example.StockExchangeLLDDesign.data.impl;

import com.example.StockExchangeLLDDesign.data.IOrderBook;
import com.example.StockExchangeLLDDesign.models.Order;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderBook implements IOrderBook {

    private final ConcurrentHashMap<String, List<Order>> orderBook=new ConcurrentHashMap<>();
    @Override
    public void addOrder(Order order) {

    }

    @Override
    public void removeOrder(String orderId, String symbol) {

    }

    @Override
    public Order getOrders(String stockSymbol) {
        return null;
    }

    @Override
    public boolean updateOrder(Order updatedOrder) {
        return false;
    }
}

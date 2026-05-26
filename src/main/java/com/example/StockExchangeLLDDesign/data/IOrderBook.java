package com.example.StockExchangeLLDDesign.data;

import com.example.StockExchangeLLDDesign.models.Order;
import com.example.StockExchangeLLDDesign.models.OrderType;

import java.util.List;
import java.util.Optional;

public interface IOrderBook {

    void addOrder(Order order);
    boolean removeOrder(String orderId,String symbol);

    List<Order> getOrders(String stockSymbol);

    boolean updateOrder(Order updatedOrder);

    Optional<Order> getOrderByOrderId(String orderId);

}

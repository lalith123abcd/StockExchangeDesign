package com.example.StockExchangeLLDDesign.data;

import com.example.StockExchangeLLDDesign.models.Order;
import com.example.StockExchangeLLDDesign.models.OrderType;

public interface IOrderBook {

    void addOrder(Order order);
    void removeOrder(String orderId,String symbol);

    Order getOrders(String stockSymbol);

    boolean updateOrder(Order updatedOrder);

}

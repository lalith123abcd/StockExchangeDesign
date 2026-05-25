package com.example.StockExchangeLLDDesign.services.strategies.impl;

import com.example.StockExchangeLLDDesign.models.Order;
import com.example.StockExchangeLLDDesign.models.OrderStatus;
import com.example.StockExchangeLLDDesign.models.OrderType;
import com.example.StockExchangeLLDDesign.models.Trade;
import com.example.StockExchangeLLDDesign.services.strategies.OrderMatchingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FIFOMatchingStrategy implements OrderMatchingStrategy {
    @Override
    public String getStrategyName() {
        return "";
    }

    @Override
    public List<Trade> matchOrder(Order newOrder, List<Order> existingOrders) {

        if(newOrder.getOrderType()== OrderType.BUY){
            return matchBuyOrders(newOrder,existingOrders);
        }
        else{
            return matchSellOrders(newOrder,existingOrders);
        }
    }

    List<Trade> matchBuyOrders(Order buyOrder,List<Order> existingOrders){
        List<Trade> trades=new ArrayList<>();

        List<Order> matchingSellOrders=
        existingOrders.stream()
                .filter(order -> order.getOrderType()==OrderType.SELL)
                .filter(order -> order.getStockId().equals(buyOrder.getStockId()))
                .filter(order -> order.getPrice()<=buyOrder.getPrice())
                .filter(order -> order.getOrderStatus().equals(OrderStatus.ACCEPTED))
                .sorted(Comparator.comparing(Order::getPrice).thenComparing(Order::getOrderAcceptedTimeStamp))
                .collect(Collectors.toList());

        int remainingQuantity=buyOrder.getRemainingQuantity();

        for(Order sellOrder:matchingSellOrders){
            int tradeQuantities=Math.min(remainingQuantity,sellOrder.getRemainingQuantity());
            Double tradePrice=sellOrder.getPrice();

            Trade trade=Trade.builder()
                    .buyerOrderId(buyOrder.getOrderId())
                    .sellerOrderId(sellOrder.getOrderId())
                    .stockId(buyOrder.getStockId())
                    .quantity(tradeQuantities)
                    .price(tradePrice)
                    .build();
            trades.add(trade);

            buyOrder.setFilledQuantity(buyOrder.getFilledQuantity()+tradeQuantities);

            buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity()-tradeQuantities);
            sellOrder.setFilledQuantity(sellOrder.getFilledQuantity()+tradeQuantities);

            sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity()-tradeQuantities);
            remainingQuantity-=tradeQuantities;
            log.info(" trade {} -{} -{} -{} -{}  ",trade.getTradeId(),trade.getBuyerOrderId(),trade.getSellerOrderId(),trade.getQuantity(),trade.getPrice() );
        }

    }

    List<Trade> matchSellOrders(Order newOrder,List<Order> existingOrders){

    }
}

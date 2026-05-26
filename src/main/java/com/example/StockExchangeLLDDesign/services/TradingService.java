package com.example.StockExchangeLLDDesign.services;

import com.example.StockExchangeLLDDesign.data.IOrderBook;
import com.example.StockExchangeLLDDesign.dtos.OrderRequest;
import com.example.StockExchangeLLDDesign.models.Order;
import com.example.StockExchangeLLDDesign.models.OrderStatus;
import com.example.StockExchangeLLDDesign.models.OrderType;
import com.example.StockExchangeLLDDesign.models.Trade;
import com.example.StockExchangeLLDDesign.services.strategies.OrderMatchingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradingService implements ITradingService {

    private final IOrderBook orderBook;
    private final OrderMatchingStrategy orderMatchingStrategy;

    private final ExecutorService executorService=Executors.newFixedThreadPool(10);
    private final ITradeService tradeService;

    public Order placeOrder(OrderRequest orderRequest){
        Order order = Order.builder()
                .userId(orderRequest.getUserId())
                .orderType(orderRequest.getOrderType())
                .stockSymbol(orderRequest.getStockSymbol())
                .quantity(orderRequest.getQuantity())
                .price(orderRequest.getPrice())
                .build();

        order.setOrderAcceptedTimeStamp(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setRemainingQuantity(order.getQuantity());

        orderBook.addOrder(order);

        CompletableFuture.runAsync(()->
        {
            try {
                executeOrderMatch(order);
            }catch (Exception e){
                log.info("Error during exceute order match",e);
            }
        },executorService);

        return order;
    }


    public  void executeOrderMatch(Order newOrder){
        String stockSymbol=newOrder.getStockSymbol();

        List<Order> existingOrders=orderBook.getOrders(stockSymbol);

        existingOrders=existingOrders.stream().filter(order -> !order.getOrderId().equals(newOrder.getOrderId())).collect(Collectors.toList());

        List<Trade> executedTrades=orderMatchingStrategy.matchOrder(newOrder,existingOrders);

        if(!executedTrades.isEmpty()){
            for(Trade trade:executedTrades){
                //save trade in db
                tradeService.save(trade);
            }
            orderBook.updateOrder(newOrder);

            for(Trade trade:executedTrades){
                String otherOrderId=newOrder.getOrderType()== OrderType.BUY?trade.getSellerOrderId():trade.getBuyerOrderId();

                orderBook.getOrderByOrderId(otherOrderId).ifPresent(orderBook::updateOrder);
            }
        }



    }

    public List<Order> getOrderBooks(String symbol){
       return orderBook.getOrders(symbol);
    }



}

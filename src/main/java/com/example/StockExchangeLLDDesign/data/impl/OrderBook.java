package com.example.StockExchangeLLDDesign.data.impl;

import com.example.StockExchangeLLDDesign.data.IOrderBook;
import com.example.StockExchangeLLDDesign.models.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class OrderBook implements IOrderBook {

    private final ConcurrentHashMap<String, List<Order>> orderBook=new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, ReadWriteLock> symbolLocks=new ConcurrentHashMap<>();
    @Override
    public void addOrder(Order order) {

        String stockSymbol=order.getStockSymbol();

        ReadWriteLock lock=getOrCreate(stockSymbol);

        lock.writeLock().lock();
        try {

            orderBook.computeIfAbsent(stockSymbol,k->new ArrayList<>()).add(order);
            log.info("order added to orderbook {} -{}- {}-{}", order.getUserId(),order.getOrderId(),order.getOrderType(),order.getStockSymbol());

        }finally {
            lock.writeLock().unlock();
        }

    }

    public ReadWriteLock getOrCreate(String stockSymbol){
        return symbolLocks.computeIfAbsent(stockSymbol,k-> new ReentrantReadWriteLock());
    }
    @Override
    public boolean removeOrder(String orderId, String stockSymbol) {
        ReadWriteLock lock = getOrCreate(stockSymbol);
        lock.writeLock().lock();

        try {
            List<Order> orders=orderBook.get(stockSymbol);

            if(orders!=null){
               boolean removed= orders.removeIf(order -> order.getOrderId().equals(orderId));

               if(removed)log.info("order removed from orderbook");
               else log.info("order not removed");
               return true;
            }
            return false;
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public Optional<Order> getOrderBySymbol(String symbol){
        ReadWriteLock lock=getOrCreate(symbol);
        lock.readLock().lock();
        try{
            return Optional.ofNullable(orderBook.get(symbol).stream().findFirst().orElse(null));

        }finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Order> getOrders(String stockSymbol) {
        ReadWriteLock lock=getOrCreate(stockSymbol);
        lock.readLock().lock();

        try{
           return orderBook.get(stockSymbol);
        }finally {
            lock.readLock().unlock();
        }
    }

    public Optional<Order> getOrderByOrderId(String orderId) {

        for(Map.Entry<String ,List<Order>> entry:orderBook.entrySet()){
            String stockSymbol=entry.getKey();
            ReadWriteLock lock = getOrCreate(stockSymbol);
            lock.readLock().lock();

            try{
                List<Order> orderList=entry.getValue();
                for(Order order:orderList){
                    if(order.getOrderId().equals(orderId)) {
                        return Optional.of(order);
                    }
                }
            }
            finally {
                lock.readLock().unlock();
            }


        }
        return Optional.empty();
    }


        @Override
    public boolean updateOrder(Order updatedOrder) {
        String stockSymbol = updatedOrder.getStockSymbol();
        ReadWriteLock lock = getOrCreate(stockSymbol);

        lock.writeLock().lock();

        try {
            List<Order> orders = orderBook.get(stockSymbol);
            if(orders != null) {
                for(int i = 0; i < orders.size(); i++) {
                    if(orders.get(i).getOrderId().equals(updatedOrder.getOrderId())) {
                        orders.set(i, updatedOrder);
                        log.info("Order updated in order book");
                        return true;
                    }
                }
            }
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }
}

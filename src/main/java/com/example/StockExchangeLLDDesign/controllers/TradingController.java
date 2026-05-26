package com.example.StockExchangeLLDDesign.controllers;

import com.example.StockExchangeLLDDesign.dtos.OrderRequest;
import com.example.StockExchangeLLDDesign.models.Order;
import com.example.StockExchangeLLDDesign.services.ITradingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
public class TradingController {
    private final ITradingService tradingService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest){
        Order order=tradingService.placeOrder(orderRequest);
        return ResponseEntity.ok(order);
    }


    @GetMapping("/orderBook/{symbol}")
    public ResponseEntity<List<Order>> getOrderBook(@PathVariable String symbol) {
        List<Order> orderBook = tradingService.getOrderBooks(symbol);

        return ResponseEntity.ok(orderBook);
    }
}



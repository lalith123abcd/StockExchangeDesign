package com.example.StockExchangeLLDDesign.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order{
    @Builder.Default
    private String orderId= UUID.randomUUID().toString();

    @NotBlank(message = "userid required")
    private String userId;

    @NotNull(message = "order type is req")
    private OrderType orderType;

    @NotBlank(message = "stock id is req")
    private String stockId;

    private int quantity;

    private double price;

    @Builder.Default
    private OrderStatus orderStatus=OrderStatus.ACCEPTED;

    @Builder.Default
    private int filledQuantity=0;

    @Builder.Default
    private int remainingQuantity=0;

    @Builder.Default
    private LocalDateTime orderAcceptedTimeStamp=LocalDateTime.now();



}

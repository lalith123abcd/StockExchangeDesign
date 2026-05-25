package com.example.StockExchangeLLDDesign.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Trade {
    @Builder.Default
    private String tradeId= UUID.randomUUID().toString();

    @NotBlank
    private String buyerOrderId;

    @NotBlank
    private String sellerOrderId;

    private String stockId;

    private int quantity;

    private double price;


}

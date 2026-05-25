package com.example.StockExchangeLLDDesign.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {
    @Builder.Default
    private String stockId= UUID.randomUUID().toString();

    @NotBlank(message = "stock name is req")
    private String stockName;

    @NotBlank(message = "symbol is req")
    private String stockSymbol;

    @NotNull(message = "price is req")
    private double stockPrice;
}

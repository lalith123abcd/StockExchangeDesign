package com.example.StockExchangeLLDDesign.models;

import jakarta.validation.constraints.Email;
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
public class User {

    @Builder.Default
    private String userId=UUID.randomUUID().toString();

    @NotBlank(message = "username is required")
    private String userName;

    @Email
    private String email;
}

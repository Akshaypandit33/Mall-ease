package com.akshay.backend.Controller.Client.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartRequest {
    private Long productId;
    private int quantity;
}

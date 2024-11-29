package com.akshay.backend.Controller.Client.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItems {
   private Long cartItemId;
   private int quantity;
}

package com.akshay.backend.Service;

import com.akshay.backend.Model.Cart;
import com.akshay.backend.Model.CartItem;
import com.akshay.backend.Model.User;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    public Cart createCart(User user);

    CartItem addProductToCart(int userId, Long productId, int quantity) throws Exception;

    List<CartItem> getCartItems(Long cartId) throws Exception;

    Cart getAllCart(User user) throws Exception;
    void removeCartItem(Long id);


    void updateCartQuantity(long cartItemId, int newQuantity) throws Exception;
}

package com.akshay.backend.ServiceImplementation;

import com.akshay.backend.Model.*;
import com.akshay.backend.Repository.CartItemRepository;
import com.akshay.backend.Repository.CartRepository;
import com.akshay.backend.Repository.ProductRepository;
import com.akshay.backend.Repository.UserRepository;
import com.akshay.backend.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImplementation implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public Cart createCart(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setCreatedOn(LocalDateTime.now());
        newCart.setUpdatedOn(LocalDateTime.now());
        newCart.setStatus(CartStatus.ACTIVE);
        return cartRepository.save(newCart);
    }

    @Override
    public CartItem addProductToCart(int userId, Long productId, int quantity) throws Exception {
//         Find or create a cart for the user
        Cart cart = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    try {
                        return createCart(userRepository.findById(userId)
                                .orElseThrow(() -> new Exception("User not found")));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });


        // Find the product by ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found or not available at this moment"));

        // Check if the product stock is sufficient
        if (product.getStockQuantity() < quantity) {
            throw new Exception("Available quantity is " + product.getStockQuantity());
        }

        // Check if the product is already in the cart
        CartItem existingCartItem = cartItemRepository.findByCartIdAndProductId(cart.getCartId(), productId).orElse(null);

        if (existingCartItem != null) {
            // Update quantity and total price if the product is already in the cart
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setTotalPrice(product.getPrice().multiply(new BigDecimal(existingCartItem.getQuantity())));
            existingCartItem.setAddedOn(LocalDateTime.now());
            cartItemRepository.save(existingCartItem);
            cart.setUpdatedOn(LocalDateTime.now());
            cartRepository.save(cart);
            return existingCartItem;
        }

        // Add new product to the cart
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(product.getPrice().multiply(new BigDecimal(quantity)));
        cartItem.setAddedOn(LocalDateTime.now());

        cart.setUpdatedOn(LocalDateTime.now());
        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getCartItems(Long cartId) throws Exception {

        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    public Cart getAllCart(User user) throws Exception {
        Cart cart= cartRepository.findByUserIdAndStatus(user.getId(), CartStatus.ACTIVE).get();
        cart.calculateTotalPrice();
        return cart;
    }

    @Override
    public void removeCartItem(Long id) {
        Cart cart = new Cart();
        cart.calculateTotalPrice();
        cartItemRepository.deleteById(id);
    }


    @Override
    public void updateCartQuantity(long cartItemId, int newQuantity) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new Exception("This item not found in cart"));
        if(newQuantity<1)
        {
            cartItemRepository.deleteById(cartItemId);
            return;
        }
        cartItem.setQuantity(newQuantity);
        cartItem.setTotalPrice(cartItem.getProduct().getPrice().multiply(new BigDecimal(newQuantity)));
        cartItemRepository.save(cartItem);
    }


}

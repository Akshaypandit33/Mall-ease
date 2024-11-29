package com.akshay.backend.Controller.Client;

import com.akshay.backend.Controller.Client.Request.AddProductToCartRequest;
import com.akshay.backend.Controller.Client.Request.UpdateCartItems;
import com.akshay.backend.Model.Cart;
import com.akshay.backend.Model.CartItem;
import com.akshay.backend.Model.CartStatus;
import com.akshay.backend.Model.User;
import com.akshay.backend.Repository.CartItemRepository;
import com.akshay.backend.Repository.CartRepository;
import com.akshay.backend.Service.CartService;
import com.akshay.backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final UserService userService;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

//    @PostMapping("/new")
//    public Cart createCart(@RequestHeader("Authorization") String token) throws Exception {
//        User user= userService.getUserFromToken(token);
//
//        return cartService.createCart(user);
//    }

    @PostMapping("/items")
    public Cart addProductToCart(@RequestBody AddProductToCartRequest request, @RequestHeader("Authorization") String token) throws Exception {
        int userId=userService.getUserFromToken(token).getId();
        cartService.addProductToCart(userId,request.getProductId(),request.getQuantity());
        return getCart(token);
    }
    @GetMapping("")
    public Cart getCart(@RequestHeader("Authorization") String token) throws Exception {
        User user= userService.getUserFromToken(token);
        return cartService.getAllCart(user);
    }
//    @GetMapping("/")
//    public List<CartItem> getAllItemsFromCart(@RequestHeader("Authorization") String token) throws Exception {
//        int userId= userService.getUserFromToken(token).getId();
//        if(cartRepository.findByUserIdAndStatus(userId,CartStatus.ACTIVE).isPresent())
//        {
//            return cartService.getCartItems(cartRepository.findByUserIdAndStatus(userId,CartStatus.ACTIVE).get().getCartId());
//        }
//       return null;
//    }

    @DeleteMapping("/remove/{productId}")
    public Cart removeFromCart(@PathVariable Long productId, @RequestHeader("Authorization") String token) throws Exception {
        cartService.removeCartItem(productId);
        return getCart(token);

    }

    @PutMapping("/update")
    public Cart updateCartItem(@RequestBody UpdateCartItems req ,@RequestHeader("Authorization") String token) throws Exception {
        cartService.updateCartQuantity(req.getCartItemId(),req.getQuantity());
        return getCart(token);
    }
}


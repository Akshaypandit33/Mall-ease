package com.akshay.backend.ServiceImplementation;

import com.akshay.backend.Model.*;
import com.akshay.backend.Repository.PaymentRepository;
import com.akshay.backend.Repository.ProductRepository;
import com.akshay.backend.Service.CartService;
import com.akshay.backend.Service.PaymentService;
import com.akshay.backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImplementation implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CartService cartService;
    private final UserService userService;
    private final ProductRepository productRepository;
    @Override
    public Payment processPayment(User user) throws Exception {
        Cart cart=cartService.getAllCart(user);
        Payment newOrder= new Payment();
        newOrder.setUser(user);
        newOrder.setCart(cart);
        newOrder.setTotalAmount(cart.getTotalPrice());
        newOrder.setStatus(PaymentStatus.PENDING);
        return paymentRepository.save(newOrder);
    }

    @Override
    public Payment getPaymentDetails(UUID paymentId, User user) throws Exception {
        Payment payment= paymentRepository.findById(paymentId).orElseThrow(
                () -> new Exception("Payment not found")
        );
        if(payment.getUser().equals(user) || user.getRole().equals(Role.ADMIN))
        {
            return payment;
        }
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment updatePaymentStatus(UUID paymentId, String transactionId, PaymentStatus paymentStatus,User user) throws Exception {
        Payment payment= getPaymentDetails(paymentId,user);
        payment.setStatus(paymentStatus);
        if(paymentStatus.equals(PaymentStatus.COMPLETED))
        {
            payment.setTransactionId(transactionId);
            payment.setPaymentDate(LocalDateTime.now());
            payment.getCart().setStatus(CartStatus.ORDERED);

//            after payment reduce the stock quantity of the product
            List<CartItem> cartItems= payment.getCart().getItems();
            for (CartItem item: cartItems)
            {
                Product product= item.getProduct();
                int quantity=item.getQuantity();
                product.setStockQuantity(product.getStockQuantity()-quantity);
                productRepository.save(product);

            }


        }
        return paymentRepository.save(payment);
    }


}

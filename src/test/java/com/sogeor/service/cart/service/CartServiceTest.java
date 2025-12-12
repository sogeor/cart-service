package com.sogeor.service.cart.service;

import com.sogeor.service.cart.domain.Cart;
import com.sogeor.service.cart.domain.CartItem;
import com.sogeor.service.cart.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart testCart;

    private final String userId = "user123";

    @BeforeEach
    void setUp() {
        testCart = new Cart();
        testCart.setId("cart1");
        testCart.setUserId(userId);
        testCart.setItems(new ArrayList<>());
    }

    @Test
    void getCart_whenExists_returnsCart() {
        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(testCart));

        StepVerifier.create(cartService.getCart(userId)).expectNext(testCart).verifyComplete();
    }

    @Test
    void getCart_whenNotExists_createsNewCart() {
        when(cartRepository.findByUserId(userId)).thenReturn(Mono.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(cartService.getCart(userId))
                    .expectNextMatches(cart -> cart.getUserId().equals(userId) && cart.getItems().isEmpty())
                    .verifyComplete();

        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void addToCart_addsNewItem() {
        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(testCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(cartService.addToCart(userId, "prod1", 2))
                    .expectNextMatches(cart -> cart.getItems().size() == 1 &&
                                               cart.getItems().get(0).getProductId().equals("prod1") &&
                                               cart.getItems().get(0).getQuantity() == 2)
                    .verifyComplete();
    }

    @Test
    void addToCart_updatesExistingItem() {
        testCart.getItems().add(new CartItem("prod1", 1));
        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(testCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(cartService.addToCart(userId, "prod1", 2))
                    .expectNextMatches(cart -> cart.getItems().size() == 1 &&
                                               cart.getItems().get(0).getProductId().equals("prod1") &&
                                               cart.getItems().get(0).getQuantity() == 3)
                    .verifyComplete();
    }

    @Test
    void updateQuantity_updatesItem() {
        testCart.getItems().add(new CartItem("prod1", 1));
        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(testCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(cartService.updateQuantity(userId, "prod1", 5))
                    .expectNextMatches(cart -> cart.getItems().get(0).getQuantity() == 5)
                    .verifyComplete();
    }

    @Test
    void removeProduct_removesItem() {
        testCart.getItems().add(new CartItem("prod1", 1));
        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(testCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(cartService.removeProduct(userId, "prod1"))
                    .expectNextMatches(cart -> cart.getItems().isEmpty())
                    .verifyComplete();
    }

    @Test
    void clearCart_removesAllItems() {
        testCart.getItems().add(new CartItem("prod1", 1));
        testCart.getItems().add(new CartItem("prod2", 2));
        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(testCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(cartService.clearCart(userId))
                    .expectNextMatches(cart -> cart.getItems().isEmpty())
                    .verifyComplete();
    }

}

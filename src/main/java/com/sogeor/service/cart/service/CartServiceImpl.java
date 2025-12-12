package com.sogeor.service.cart.service;

import com.sogeor.service.cart.domain.Cart;
import com.sogeor.service.cart.domain.CartItem;
import com.sogeor.service.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public Mono<@NotNull Cart> getCart(String userId) {
        return cartRepository.findByUserId(userId).switchIfEmpty(Mono.defer(() -> createNewCart(userId)));
    }

    private Mono<@NotNull Cart> createNewCart(String userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItems(new ArrayList<>());
        cart.setCreatedAt(Instant.now());
        cart.setUpdatedAt(Instant.now());
        return cartRepository.save(cart);
    }

    @Override
    public Mono<@NotNull Cart> addToCart(String userId, String productId, int quantity) {
        return getCart(userId).flatMap(cart -> {
            Optional<CartItem> existingItem = cart.getItems()
                                                  .stream()
                                                  .filter(item -> item.getProductId().equals(productId))
                                                  .findFirst();

            if (existingItem.isPresent()) {
                existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
            } else {
                cart.getItems().add(new CartItem(productId, quantity));
            }
            cart.setUpdatedAt(Instant.now());
            return cartRepository.save(cart);
        });
    }

    @Override
    public Mono<@NotNull Cart> updateQuantity(String userId, String productId, int quantity) {
        return getCart(userId).flatMap(cart -> {
            cart.getItems()
                .stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
            cart.setUpdatedAt(Instant.now());
            return cartRepository.save(cart);
        });
    }

    @Override
    public Mono<@NotNull Cart> removeProduct(String userId, String productId) {
        return getCart(userId).flatMap(cart -> {
            cart.getItems().removeIf(item -> item.getProductId().equals(productId));
            cart.setUpdatedAt(Instant.now());
            return cartRepository.save(cart);
        });
    }

    @Override
    public Mono<@NotNull Cart> clearCart(String userId) {
        return getCart(userId).flatMap(cart -> {
            cart.getItems().clear();
            cart.setUpdatedAt(Instant.now());
            return cartRepository.save(cart);
        });
    }

}

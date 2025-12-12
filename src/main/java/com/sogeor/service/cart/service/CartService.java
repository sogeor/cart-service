package com.sogeor.service.cart.service;

import com.sogeor.service.cart.domain.Cart;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public interface CartService {

    Mono<@NotNull Cart> getCart(String userId);

    Mono<@NotNull Cart> addToCart(String userId, String productId, int quantity);

    Mono<@NotNull Cart> updateQuantity(String userId, String productId, int quantity);

    Mono<@NotNull Cart> removeProduct(String userId, String productId);

    Mono<@NotNull Cart> clearCart(String userId);

}

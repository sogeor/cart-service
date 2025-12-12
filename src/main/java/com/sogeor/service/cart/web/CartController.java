package com.sogeor.service.cart.web;

import com.sogeor.service.cart.domain.Cart;
import com.sogeor.service.cart.service.CartService;
import com.sogeor.service.cart.web.dto.CartItemRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Mono<@NotNull Cart> getCart(Principal principal) {
        return cartService.getCart(principal.getName());
    }

    @PostMapping("/add")
    public Mono<@NotNull Cart> addToCart(Principal principal, @RequestBody @Valid CartItemRequest request) {
        return cartService.addToCart(principal.getName(), request.getProductId(), request.getQuantity());
    }

    @PutMapping("/update")
    public Mono<@NotNull Cart> updateQuantity(Principal principal, @RequestBody @Valid CartItemRequest request) {
        return cartService.updateQuantity(principal.getName(), request.getProductId(), request.getQuantity());
    }

    @DeleteMapping("/remove/{productId}")
    public Mono<@NotNull Cart> removeProduct(Principal principal, @PathVariable String productId) {
        return cartService.removeProduct(principal.getName(), productId);
    }

    @DeleteMapping("/clear")
    public Mono<@NotNull Cart> clearCart(Principal principal) {
        return cartService.clearCart(principal.getName());
    }

}

package com.sogeor.service.cart.repository;

import com.sogeor.service.cart.domain.Cart;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveMongoRepository<@NotNull Cart, @NotNull String> {

    Mono<@NotNull Cart> findByUserId(String userId);

}

package com.sogeor.service.cart.messaging;

import com.sogeor.service.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDeletedConsumer {

    private final CartRepository cartRepository;

    @KafkaListener(topics = "product-updates", groupId = "cart-service")
    public void consumeProductEvents(Map<String, Object> message) {
        String eventType = (String) message.get("eventType");

        if ("PRODUCT_DELETED".equals(eventType)) {
            String productId = (String) message.get("productId");
            log.info("Received PRODUCT_DELETED event for productId: {}", productId);

            removeProductFromCarts(productId).subscribe();
        }
    }

    private Mono<@NotNull Void> removeProductFromCarts(String productId) {
        return cartRepository.findAll()
                             .filter(cart -> cart.getItems()
                                                 .stream()
                                                 .anyMatch(item -> item.getProductId().equals(productId)))
                             .flatMap(cart -> {
                                 cart.getItems().removeIf(item -> item.getProductId().equals(productId));
                                 return cartRepository.save(cart);
                             })
                             .then();
    }

}

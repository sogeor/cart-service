package com.sogeor.service.cart.messaging;

import com.sogeor.service.cart.domain.CartItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartEventProducer {

    private static final String TOPIC = "cart-events";

    private final KafkaTemplate<@NotNull String, @NotNull Object> kafkaTemplate;

    public void sendCartAbandonedEvent(String userId, List<CartItem> items) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "CART_ABANDONED");
        event.put("userId", userId);
        event.put("items", items);
        event.put("timestamp", Instant.now());

        log.info("Sending CART_ABANDONED event for user: {}", userId);
        kafkaTemplate.send(TOPIC, userId, event);
    }

}

package com.sogeor.service.cart.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDeletedEvent {

    private String eventType;

    private String productId;

    private Instant timestamp;

}

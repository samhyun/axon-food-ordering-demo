package com.example.food.command;

import com.example.food.coreapi.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class FoodCart {

    @AggregateIdentifier
    private UUID foodCartId;
    private Map<UUID, Integer> selectedProducts;

    @CommandHandler
    public FoodCart(CreateFoodCartCommand command) {
        UUID foodCartId = UUID.randomUUID();
        AggregateLifecycle.apply(new FoodCartCreatedEvent(foodCartId));
    }

    @CommandHandler
    public FoodCart(SelectProductCommand command) {
        AggregateLifecycle.apply(new ProductSelectedEvent(
                foodCartId, command.getProductId(), command.getQuantity()
        ));
    }


    @CommandHandler
    public FoodCart(DeselectProductCommand command) throws ProductDesectionException {
        UUID productId = command.getProductId();
        if (!selectedProducts.containsKey(productId)) {
            throw new ProductDesectionException();
        }
        AggregateLifecycle.apply(new ProductDeselectedEvent(
                foodCartId, productId, command.getQuantity()
        ));
    }

    @EventSourcingHandler
    public void on(FoodCartCreatedEvent event) {
        foodCartId = event.getFoodCartId();
        selectedProducts = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(ProductSelectedEvent event) {
        selectedProducts.merge(event.getProductId(), event.getQuantity(), Integer::sum);
    }
}

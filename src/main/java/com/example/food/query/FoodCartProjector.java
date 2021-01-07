package com.example.food.query;

import com.example.food.coreapi.FindFoodCartQuery;
import com.example.food.coreapi.FoodCartCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class FoodCartProjector {

    private final FoodCartViewRepository foodCartViewRepository;

    @EventHandler
    public void on(FoodCartCreatedEvent event) {
        FoodCartView foodCartView = new FoodCartView(event.getFoodCartId(), Collections.emptyMap());
        foodCartViewRepository.save(foodCartView);
    }

    @QueryHandler
    public FoodCartView handle(FindFoodCartQuery query) {
        return foodCartViewRepository.findById(query.getFoodCartID()).orElse(null);
    }
}

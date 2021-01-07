package com.example.food.gui;

import com.example.food.coreapi.CreateFoodCartCommand;
import com.example.food.coreapi.FindFoodCartQuery;
import com.example.food.query.FoodCartView;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class FoodOrderingController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/foodcart")
    public void handle() {
        commandGateway.send(new CreateFoodCartCommand());
    }

    @GetMapping("/foodcart/{id}")
    public CompletableFuture<FoodCartView> handle(@PathVariable String id) {
        return queryGateway.query(
                new FindFoodCartQuery(UUID.fromString(id)),
                ResponseTypes.instanceOf(FoodCartView.class)
        );
    }
}

package com.example.food.command;

import com.example.food.coreapi.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class FoodCartTest {

    private FixtureConfiguration<FoodCart> fixture;

    private UUID foodCartId;
    private UUID productId;
    private int quantity;

    @BeforeEach
    public void setup() {
        fixture = new AggregateTestFixture<>(FoodCart.class);

        foodCartId = UUID.randomUUID();
        productId = UUID.randomUUID();
        quantity = 3;
    }

    @Test
    @DisplayName("푸드 카트 생성 커맨드 테스트")
    public void food_cart_create_command_test() {
        fixture.given()
                .when(new CreateFoodCartCommand(foodCartId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new FoodCartCreatedEvent(foodCartId));
    }

    @Test
    @DisplayName("푸드 카트 상품 선택 커멘드 테스트")
    public void food_cart_select_command_test() {
        fixture.given(new FoodCartCreatedEvent(foodCartId))
                .when(new SelectProductCommand(foodCartId, productId, quantity))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ProductSelectedEvent(foodCartId, productId, quantity));
    }

    @Test
    @DisplayName("푸드 카트 상품 선택 취소 커멘드 테스트")
    public void food_cart_deselect_command_test() {
        fixture.given(new FoodCartCreatedEvent(foodCartId), new ProductSelectedEvent(foodCartId, productId, quantity))
                .when(new DeselectProductCommand(foodCartId, productId, quantity))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ProductDeselectedEvent(foodCartId, productId, quantity));
    }

}

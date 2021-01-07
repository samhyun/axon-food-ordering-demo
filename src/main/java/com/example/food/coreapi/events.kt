package com.example.food.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*


data class FoodCartCreatedEvent(
    val foodCartId: UUID
)

data class ProductSelectedEvent(
    val foodCartId: UUID,
    val productId: UUID,
    val quantity: Int
)

data class ProductDeselectedEvent(
    val foodCartId: UUID,
    val productId: UUID,
    val quantity: Int
)

data class OrderConfirmedEvent(
    val foodCartId: UUID
)
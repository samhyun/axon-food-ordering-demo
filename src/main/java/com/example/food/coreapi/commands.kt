package com.example.food.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*


class CreateFoodCartCommand

data class SelectProductCommand(
    @TargetAggregateIdentifier val foodCartId: UUID,
    val productId: UUID,
    val quantity: Int
)

data class DeselectProductCommand(
    @TargetAggregateIdentifier val foodCartId: UUID,
    val productId: UUID,
    val quantity: Int
)

data class ConfirmOrderCommand(
    @TargetAggregateIdentifier val foodCartId: UUID
)
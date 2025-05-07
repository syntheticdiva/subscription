package com.java.subscription.controller;

import com.java.subscription.exception.ErrorResponse;
import com.java.subscription.dto.SubscriptionRequest;
import com.java.subscription.dto.SubscriptionResponse;
import com.java.subscription.dto.TopSubscriptionResponse;
import com.java.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "API для управления подписками пользователей")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @Operation(
            summary = "Добавить подписку",
            description = "Добавляет новую подписку для указанного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Подписка успешно создана",
                            content = @Content(schema = @Schema(implementation = SubscriptionResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные параметры запроса",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    public ResponseEntity<SubscriptionResponse> addSubscription(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subscriptionService.addSubscription(userId, request));
    }

    @GetMapping
    @Operation(
            summary = "Получить подписки",
            description = "Возвращает список всех подписок пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список подписок",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SubscriptionResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptions(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByUserId(userId));
    }

    @DeleteMapping("/{subscriptionId}")
    @Operation(
            summary = "Удалить подписку",
            description = "Удаляет указанную подписку пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Подписка успешно удалена"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь или подписка не найдены",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId
    ) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/top")
    @Operation(
            summary = "ТОП-3 популярных подписок",
            description = "Возвращает три самые популярные подписки среди всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список популярных подписок",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TopSubscriptionResponse.class))
                            )
                    )
            }
    )
    public ResponseEntity<List<TopSubscriptionResponse>> getTopSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTop3Subscriptions());
    }

}

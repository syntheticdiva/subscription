package com.java.subscription.controller;

import com.java.subscription.dto.AvailableServiceResponse;
import com.java.subscription.enums.DigitalService;
import com.java.subscription.mapper.ServiceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Services", description = "API для работы с доступными сервисами")
public class ServiceController {

    private final ServiceMapper serviceMapper;

    @GetMapping
    @Operation(summary = "Получить все доступные сервисы")
    public List<AvailableServiceResponse> getAllServices() {
        return Arrays.stream(DigitalService.values())
                .map(serviceMapper::toAvailableServiceResponse)
                .toList();
    }
}
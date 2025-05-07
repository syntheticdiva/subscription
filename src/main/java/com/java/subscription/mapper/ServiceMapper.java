package com.java.subscription.mapper;

import com.java.subscription.dto.AvailableServiceResponse;
import com.java.subscription.enums.DigitalService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(target = "code", expression = "java(service.name())")
    @Mapping(target = "displayName", source = "service.displayName")
    @Mapping(target = "description", expression = "java(getServiceDescription(service))")
    AvailableServiceResponse toAvailableServiceResponse(DigitalService service);

    default String getServiceDescription(DigitalService service) {
        return switch (service) {
            case YOUTUBE_PREMIUM -> "Premium подписка на YouTube без рекламы";
            case VK_MUSIC -> "Музыкальный сервис ВКонтакте";
            case YANDEX_PLUS -> "Подписка на сервисы Яндекса";
            case NETFLIX -> "Стриминговый сервис фильмов и сериалов";
            case SPOTIFY -> "Музыкальная платформа с миллионами треков";
            case APPLE_MUSIC -> "Музыкальный сервис от Apple";
        };
    }
}
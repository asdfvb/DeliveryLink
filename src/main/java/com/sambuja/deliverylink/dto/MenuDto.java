package com.sambuja.deliverylink.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum MenuDto {
    //직화무뼈
    DIRECT_FIRE_NO_BONE(3),
    //직화튤립
    DIRECT_FIRE_HALF_BONE(4),
    //직화통뼈
    DIRECT_FIRE_ENTIRE_BONE(4),
    //국물무뼈
    SOUP_NO_BONE(3),
    //국물통뼈
    SOUP_ENTIRE_BONE(5),
    //아이스팩
    ICE_PACK(2)
    ;

    private int height;

    MenuDto(int heigth) {
        this.height = heigth;
    }
}

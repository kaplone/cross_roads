package com.codingame.game;

public enum EnumCar {
    CAR_SPRITE_01("car_01.png"),
    CAR_SPRITE_02("car_02.png"),
    CAR_SPRITE_03("car_03.png");

    private String path;

    EnumCar(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

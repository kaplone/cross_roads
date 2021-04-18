package com.codingame.game;

import java.util.Arrays;

public enum EnumCar {
    CAR_SPRITE_01("car_01.png", "^"),
    CAR_SPRITE_02("car_02.png", "<"),
    CAR_SPRITE_03("car_03.png", ">");

    private String path;
    private String direction;

    EnumCar(String path, String direction) {
        this.path = path;
        this.direction = direction;
    }

    public String getPath() {
        return path;
    }

    public static String getPathByDir(String dir){
        //System.err.println(dir);
        return Arrays.stream(EnumCar.values()).filter(e -> e.direction.equals(dir)).findFirst().get().getPath();
    }
}

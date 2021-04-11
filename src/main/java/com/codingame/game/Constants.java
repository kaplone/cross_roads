package com.codingame.game;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.codingame.gameengine.module.entities.World;

public class Constants {
    public static final int VIEWER_WIDTH = World.DEFAULT_WIDTH;
    public static final int VIEWER_HEIGHT = World.DEFAULT_HEIGHT;
    
    public static final int CELL_SIZE = 101;
    public static final int CELL_OFFSET = CELL_SIZE * 2  / 3;
    public static final int CELL_OFFSET_1 = CELL_SIZE  / 3;
    public static final int CELL_OFFSET_0 = 0;
    public static final int CELL_OFFSET_MINUS_DIV_7 = - CELL_SIZE / 7;
    public static final int CELL_OFFSET_DIV_4 = CELL_SIZE / 4;
    public static final int CELL_OFFSET_MINUS_DIV_11 = - CELL_SIZE / 11;
    public static final int ROWS = 6;
    public static final int COLUMNS = 15;
    
    public static final String CAR_SPRITE_01 = "car_01.png";
    public static final String CAR_SPRITE_02 = "car_02.png";
    public static final String EGGS_SPRITE = "feux_3rouge.png";
    public static final String BACKGROUND_SPRITE = "cross-full.png";

    public static final String FEU_VERT = "feu_vert.png";
    public static final String FEU_ORANGE = "feu_orange.png";
    public static final String FEU_ROUGE = "feu_rouge.png";

    public static final String V_RED = "V_RED";
    public static final String H_RED = "H_RED";
    public static final String H_GREEN = "H_GREEN";
    public static final String V_GREEN = "V_GREEN";
    public static final String WAIT_ACTION = "WAIT";
    public static final String[] ACTIONS = new String[] { V_RED, H_RED, V_GREEN, H_GREEN, WAIT_ACTION };

    public static final Map<String, Integer> LIBERATIONS_BEFORE_IN_CROSS = Stream.of(
            new AbstractMap.SimpleEntry<>("N", 7),
            new AbstractMap.SimpleEntry<>("S", 3),
            new AbstractMap.SimpleEntry<>("W", 11),
            new AbstractMap.SimpleEntry<>("E", 7))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static final Map<String, Integer> LIBERATIONS_IN_CROSS = Stream.of(
            new AbstractMap.SimpleEntry<>("N", 6),
            new AbstractMap.SimpleEntry<>("S", 4),
            new AbstractMap.SimpleEntry<>("W", 10),
            new AbstractMap.SimpleEntry<>("E", 8))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static final Map<String, Integer> LIBERATIONS_OUT_CROSS = Stream.of(
            new AbstractMap.SimpleEntry<>("N", 4),
            new AbstractMap.SimpleEntry<>("S", 6),
            new AbstractMap.SimpleEntry<>("W", 8),
            new AbstractMap.SimpleEntry<>("E", 10))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

}

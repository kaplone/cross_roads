package com.codingame.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigConverterTest {

    @Test
    public void testConverter(){
        String url = "src/test/resources/inputs_format_01.json";

        System.err.println(ConfigConverter.convertJsonToTestIn(url));
    }

}
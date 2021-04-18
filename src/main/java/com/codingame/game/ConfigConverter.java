package com.codingame.game;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.codingame.game.Constants.FAUX_RETOUR;

public class ConfigConverter {

    static public String convertJsonToTestIn(String jsonUrlIn, String jsonUrlOut){


        Path pathIn = Paths.get(jsonUrlIn);
        File fileIn = pathIn.toFile();
        Path pathOut = Paths.get(jsonUrlOut);
        File fileOut = pathOut.toFile();
        String read = "";

        ObjectMapper objectMapper = new ObjectMapper();
        GameModel gameModel = null;
        try {
            gameModel = objectMapper.readValue(fileIn, GameModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputModel inputModel = new InputModel();
        inputModel.getTitle().put(1, "test_01_01");
        inputModel.getTitle().put(2, "test_02_02");
        inputModel.setTestIn(gameModel.toString());
        inputModel.setTest(true);
        inputModel.setValidator(true);
        try{
            objectMapper.writeValue(fileOut, inputModel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameModel.toString();
    }
}








package com.codingame.game;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.*;
import com.google.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

public class Referee extends AbstractReferee {
    @Inject private SoloGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private static Map<Integer, Car> cars = new HashMap<>();
    private static Map<String, Feu> feux = new HashMap<>();

    @Override
    public void init() {
        gameManager.setFrameDuration(500);
        
        // Draw background
        graphicEntityModule.createSprite().setImage(Constants.BACKGROUND_SPRITE);
        // Draw fires
        Sprite feu_up = graphicEntityModule.createSprite().setImage(Constants.FEU_VERT)
                .setX(10 * Constants.CELL_SIZE + Constants.CELL_OFFSET_1)
                .setY(6 * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                .setAnchor(.5)
                .setZIndex(2000);
        feux.put("U", new Feu(0, feu_up, "V", "U"));

        Sprite feu_down = graphicEntityModule.createSprite().setImage(Constants.FEU_VERT)
                .setX(8 * Constants.CELL_SIZE + Constants.CELL_OFFSET_1)
                .setY(4 * Constants.CELL_SIZE )
                .setAnchor(.5)
                .setZIndex(2000);
        feux.put("D", new Feu(1, feu_down, "V", "D"));

        Sprite feu_right = graphicEntityModule.createSprite().setImage(Constants.FEU_ROUGE)
                .setRotation(Math.PI / 2)
                .setX(8 * Constants.CELL_SIZE)
                .setY(6 * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                .setAnchor(.5)
                .setZIndex(2000);
        feux.put("R", new Feu(2, feu_right, "R", "R"));

        Sprite feu_left = graphicEntityModule.createSprite().setImage(Constants.FEU_ROUGE)
                .setRotation(3 * Math.PI / 2)
                .setX(10 * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                .setY(4 * Constants.CELL_SIZE + Constants.CELL_OFFSET_1)
                .setAnchor(.5)
                .setZIndex(2000);
        feux.put("L", new Feu(3, feu_left, "R", "L"));

        
        int stackCount = Integer.valueOf(gameManager.getTestCaseInput().get(0));
        System.err.println("stackCount = "  + gameManager.getTestCaseInput().get(0));
        gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(0));

        LineStack[] stacks = new LineStack[stackCount];

        
        for (int i = 0; i < stackCount; i++) {
            String[] testInputs = gameManager.getTestCaseInput().get(i+1).split(" ");
            System.err.println("testInputs = "  + gameManager.getTestCaseInput().get(i+1));
            gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(i+1));

            //System.err.println("testInputs = "  +Arrays.toString(testInputs));
            int stackSize = Integer.parseInt(testInputs[0]);
            for (int j = 0; j < stackSize; j++) {

                Integer carId = Integer.parseInt(testInputs[j * 5 + 1]);
                Integer carSize = Integer.parseInt(testInputs[j * 5 + 2]);
                Integer carPrio = Integer.parseInt(testInputs[j * 5 + 3]);
                String dir = testInputs[j * 5 + 4];
                String turn = testInputs[j * 5 + 5];

                stacks[i] = new LineStack(j,
                        Constants.LIBERATIONS_IN_CROSS.get(dir),
                        Constants.LIBERATIONS_OUT_CROSS.get(dir),
                        Constants.LIBERATIONS_BEFORE_IN_CROSS.get(dir));
                //System.err.println(carId + " " + dir);
                Sprite carSprite = null;
                Car car = null;

                switch (dir){
                    case "U" :  carSprite = graphicEntityModule.createSprite().setImage(EnumCar.values()[(int) (Math.random() * EnumCar.values().length)].getPath())
                            .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                            .setY((5 + stackSize -j) * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                            .setAnchor(.5)
                            .setZIndex(2);
                        car = new Car(carId, carSize, carPrio, dir, turn, carSprite, 9, (5 + stackSize -j));
                        car.setOffsetX(Constants.CELL_OFFSET);
                        car.setOffsetY(Constants.CELL_OFFSET);
                        break;
                    case "D" :  carSprite = graphicEntityModule.createSprite().setImage(EnumCar.values()[(int) (Math.random() * EnumCar.values().length)].getPath())
                            .setRotation(Math.PI)
                            .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET_0)
                            .setY((4 -stackSize + j) * Constants.CELL_SIZE + Constants.CELL_OFFSET_1)
                            .setAnchor(.5)
                            .setZIndex(2);
                        car = new Car(carId, carSize, carPrio, dir, turn, carSprite, 9, (4 - stackSize + j));
                        car.setOffsetX(Constants.CELL_OFFSET_0);
                        car.setOffsetY(Constants.CELL_OFFSET_1);
                        break;
                    case "L" :  carSprite = graphicEntityModule.createSprite().setImage(EnumCar.values()[(int) (Math.random() * EnumCar.values().length)].getPath())
                            .setRotation(3 * Math.PI / 2)
                            .setX((9  + stackSize - j) * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                            .setY(5 * Constants.CELL_SIZE + Constants.CELL_OFFSET_DIV_4)
                            .setAnchor(.5)
                            .setZIndex(2);
                        car = new Car(carId, carSize, carPrio, dir, turn, carSprite, 9  + stackSize - j, 5);
                        car.setOffsetX(Constants.CELL_OFFSET_1);
                        car.setOffsetY(Constants.CELL_OFFSET_DIV_4);
                        break;
                    case "R" :  carSprite = graphicEntityModule.createSprite().setImage(EnumCar.values()[(int) (Math.random() * EnumCar.values().length)].getPath())
                            .setRotation(Math.PI / 2)
                            .setX((8 - stackSize + j) * Constants.CELL_SIZE + Constants.CELL_OFFSET_1)
                            .setY(6 * Constants.CELL_SIZE + Constants.CELL_OFFSET_MINUS_DIV_11)
                            .setAnchor(.5)
                            .setZIndex(2);

                        car = new Car(carId, carSize, carPrio, dir, turn, carSprite, 8 - stackSize + j, 6);
                        car.setOffsetX(Constants.CELL_OFFSET_1);
                        car.setOffsetY(Constants.CELL_OFFSET_MINUS_DIV_11);
                        break;
                }


                cars.put(carId, car);
                stacks[i].push(car);
                //System.err.println(stacks[i]);



            }
        }
    }

    @Override
    public void gameTurn(int turn) {
        //gameManager.getPlayer().sendInputLine(fishPosition.toString());

        gameManager.getPlayer().execute();

        try {
            List<String> outputs = gameManager.getPlayer().getOutputs();
            //System.err.println(outputs);
            String output = checkOutput(outputs);

            if (output == null){
                throw new TimeoutException();
            }

            Action action = Action.valueOf(output.toUpperCase());
            checkInvalidAction(action);

            switch (output){
                case "V_RED" : feux.get("U").setRed(); feux.get("D").setRed(); break;
                case "H_RED" : feux.get("R").setRed(); feux.get("L").setRed(); break;
                case "V_GREEN" : feux.get("R").setRed(); feux.get("L").setRed(); feux.get("U").setGreen(); feux.get("D").setGreen(); break;
                case "H_GREEN" : feux.get("U").setRed(); feux.get("D").setRed(); feux.get("R").setGreen(); feux.get("L").setGreen(); break;

            }

        } catch (TimeoutException e) {
            gameManager.loseGame("Timeout!");
        }



        updateView();
    }

    @Override
    public void onEnd() {
        //gameManager.putMetadata("eggs", String.valueOf(eggsCollected));
    }

    private void updateView() {
        feux.values().forEach(f -> f.updateEtat());

        cars.values().stream()
                //.peek(p -> System.err.println(p))
                .filter(a -> a != null && a.getSprite() != null && a.canMove())
                .forEach(c -> c.getSprite().setX(c.updatePos(true)[0] * Constants.CELL_SIZE + c.getOffsetX(), Curve.LINEAR)
                .setY(c.updatePos(false)[1] * Constants.CELL_SIZE + c.getOffsetY(), Curve.LINEAR));
        ;
    }

    private String checkOutput(List<String> outputs) {
        //System.err.println(outputs);
        if (outputs.size() != 1) {
            gameManager.loseGame("You did not send 1 output in your turn.");
        } else {
            String output = outputs.get(0);
            //System.err.println(output);
            if (!Arrays.asList(Constants.ACTIONS).contains(output)) {
                gameManager
                    .loseGame(
                        String.format(
                            "Expected output: %s but received %s",
                            Arrays.asList(Constants.ACTIONS).stream().collect(Collectors.joining(" | ")),
                            output
                        )
                    );
            } else {
                return output;
            }
        }
        return null;
    }

    private void checkInvalidAction(Action action) {
        if (action == null || !(action == Action.WAIT|| action == Action.H_RED || action == Action.V_RED || action == Action.H_GREEN || action == Action.V_GREEN)){
            gameManager.loseGame("Commande invalide.");
        }

//        if ((fishPosition.y == 0 && action == Action.UP)
//            || (fishPosition.y == Constants.ROWS - 1 && action == Action.DOWN)
//            || fishPosition.add(Coord.RIGHT).x > Constants.COLUMNS - 1) {
//            gameManager.loseGame("Your fish swum out of the game zone.");
//        }
    }

    public static Map<Integer, Car> getCars() {
        return cars;
    }


    public static Map<String, Feu> getFeux() {
        return feux;
    }

}

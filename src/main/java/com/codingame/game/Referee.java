package com.codingame.game;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.*;
import com.google.inject.Inject;
import com.google.inject.internal.cglib.core.$Constants;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Referee extends AbstractReferee {
    @Inject private SoloGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private static Map<Integer, Car> cars = new HashMap<>();
    private static Map<String, TrafficLight> feux = new HashMap<>();
    private static int readLineIndex = 0;

    private static LineFifo[] lineFifos;
    private static Text scoreText;

    private Integer score = 0;

    @Override
    public void init() {
        gameManager.setFrameDuration(500);

        scoreText = graphicEntityModule.createText(String.valueOf(0))
                .setFontSize(100)
                .setFillColor(0x000000)
                .setX(250)
                .setY(150)
                .setAnchor(.5)
                .setZIndex(20);

        // general definitions
        Integer directionsCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(readLineIndex)); // number of roads
        readLineIndex++; // 1
        List<String> directions = Arrays.asList(gameManager.getTestCaseInput().get(readLineIndex).split(" "));
        gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(readLineIndex)); // directions of roads
        readLineIndex++; // 2


        // Draw background
        graphicEntityModule.createSprite().setImage(Constants.BACKGROUND_SPRITE);

        // traffic fire definitions
        int trafficLightCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(readLineIndex)); // number of traffic lights
        readLineIndex++; // 3

        for (int f = 0; f < trafficLightCount; f ++){
            String infos = gameManager.getTestCaseInput().get(readLineIndex);
            gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(readLineIndex)); // number of traffic lights
            readLineIndex++; // 4 ... 4+f

            Integer lightId = Integer.parseInt(infos.split(" ")[0]);
            String lightDirection = infos.split(" ")[1];
            LightColor lightColor = LightColor.valueOf(infos.split(" ")[2]);
            Status status = Status.valueOf(infos.split(" ")[3]);
            Integer lightDelayToListen = Integer.parseInt(infos.split(" ")[4]);
            LightColor lightNextColor = LightColor.valueOf(infos.split(" ")[5]);
            Integer lightDelayToNextColor = Integer.parseInt(infos.split(" ")[6]);

            int xPosition = 0;
            int xOffset = Constants.CELL_OFFSET_0;
            int yPosition = 0;
            int yOffset = Constants.CELL_OFFSET_0;
            double rotateAngle = 0;
            String colorSprite = LightColor.RED == lightColor ? Constants.FEU_ROUGE : Constants.FEU_VERT;


            switch (lightDirection){
                case "N" : xPosition = 10; xOffset = Constants.CELL_OFFSET_1; yPosition = 6; yOffset = Constants.CELL_OFFSET; break;
                case "S" : xPosition = 8; xOffset = Constants.CELL_OFFSET_1; yPosition = 4; break;
                case "E" : xPosition = 8; yPosition = 6; yOffset = Constants.CELL_OFFSET; rotateAngle = Math.PI / 2; break;
                case "W" : xPosition = 10; xOffset = Constants.CELL_OFFSET; yPosition = 4; yOffset = Constants.CELL_OFFSET_1; rotateAngle = 3 *Math.PI / 2; break;
            }

            // Draw fires
            Sprite feu = graphicEntityModule.createSprite().setImage(colorSprite)
                    .setRotation(rotateAngle)
                    .setX(xPosition * Constants.CELL_SIZE + xOffset)
                    .setY(yPosition * Constants.CELL_SIZE + yOffset)
                    .setAnchor(.5)
                    .setZIndex(2000);
            feux.put(lightDirection, new TrafficLight(lightId, feu, lightDirection, lightColor, status, lightDelayToNextColor, lightDelayToListen, lightNextColor));
        }

        // fifos definitions
        int fifoCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(readLineIndex)); // number of fifos
        readLineIndex++; //
        int fifoSise = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(readLineIndex)); // fifo visibility size
        readLineIndex++; //

        LineFifo[] fullFifos = new LineFifo[fifoCount];
        lineFifos = new LineFifo[fifoCount];

        
        for (int i = 0; i < fifoCount; i++) {
            String[] testInputs = gameManager.getTestCaseInput().get(readLineIndex).split(" ");
            //System.err.println("testInputs = "  + gameManager.getTestCaseInput().get(readLineIndex));
            gameManager.getPlayer().sendInputLine(gameManager.getTestCaseInput().get(readLineIndex));
            readLineIndex++; //

            //System.err.println("testInputs = "  +Arrays.toString(testInputs));
            int fullFifoSize = Integer.parseInt(testInputs[0]);
            int remainToActive = fifoSise;
            for (int j = 0; j < fullFifoSize; j++) {

                //System.err.println("testInputs = "  +Arrays.toString(testInputs));

                Integer carId = Integer.parseInt(testInputs[j * 8 + 1]);
                String dir = testInputs[j * 8 + 2];
                String turn = testInputs[j * 8 + 3];
                Integer carSize = Integer.parseInt(testInputs[j * 8 + 4]);
                //String carColor = testInputs[j * 10 + 5];
                Integer carPassengers = Integer.parseInt(testInputs[j * 8 + 5]);
                Integer carPrio = Integer.parseInt(testInputs[j * 8 + 6]);
                Integer carPoints = Integer.parseInt(testInputs[j * 8 + 7]);
                Integer carPenalty = Integer.parseInt(testInputs[j * 8 + 8]);
                //Integer carCellPosition = Integer.parseInt(testInputs[j * 10 + 10]);


                lineFifos[i] = new LineFifo(j,
                        Constants.LIBERATIONS_IN_CROSS.get(dir),
                        Constants.LIBERATIONS_OUT_CROSS.get(dir),
                        Constants.LIBERATIONS_BEFORE_IN_CROSS.get(dir));
                //System.err.println(carId + " " + dir);
                Sprite carSprite = null;

                Car car = null;

                switch (dir){
                    case "N" :  carSprite = graphicEntityModule.createSprite().setImage(EnumCar.values()[(int) (Math.random() * EnumCar.values().length)].getPath())
                            .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                            .setY((5 + fullFifoSize -j) * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                            .setAnchor(.5)
                            .setZIndex(10);
                        car = new Car(carId, carSize, carPrio, dir, turn, carSprite, 9, (5 + fullFifoSize -j) ,carPassengers);
                        car.setOffsetX(Constants.CELL_OFFSET);
                        car.setOffsetY(Constants.CELL_OFFSET);

//                        graphicEntityModule.createGroup(carSprite, carText)
//                                .setX(car.getX() * Constants.CELL_SIZE + Constants.CELL_OFFSET)
//                                .setY(car.getY() * Constants.CELL_SIZE + Constants.CELL_OFFSET);

                        break;
                    case "S" :  carSprite = graphicEntityModule.createSprite().setImage(EnumCar.values()[(int) (Math.random() * EnumCar.values().length)].getPath())
                            .setRotation(Math.PI)
                            .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET_0)
                            .setY((4 -fullFifoSize + j) * Constants.CELL_SIZE + Constants.CELL_OFFSET_1)
                            .setAnchor(.5)
                            .setZIndex(2);
                        car = new Car(carId + 100, carSize, carPrio, dir, turn, carSprite, 9, (4 - fullFifoSize + j) ,carPassengers);
                        car.setOffsetX(Constants.CELL_OFFSET_0);
                        car.setOffsetY(Constants.CELL_OFFSET_1);
                        break;
                    case "W" :  carSprite = graphicEntityModule.createSprite().setImage(EnumCar.values()[(int) (Math.random() * EnumCar.values().length)].getPath())
                            .setRotation(3 * Math.PI / 2)
                            .setX((9  + fullFifoSize - j) * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                            .setY(5 * Constants.CELL_SIZE + Constants.CELL_OFFSET_DIV_4)
                            .setAnchor(.5)
                            .setZIndex(2);
                        car = new Car(carId + 1000, carSize, carPrio, dir, turn, carSprite, 9  + fullFifoSize - j, 5 ,carPassengers);
                        car.setOffsetX(Constants.CELL_OFFSET_1);
                        car.setOffsetY(Constants.CELL_OFFSET_DIV_4);
                        break;
                    case "E" :  carSprite = graphicEntityModule.createSprite().setImage(EnumCar.values()[(int) (Math.random() * EnumCar.values().length)].getPath())
                            .setRotation(Math.PI / 2)
                            .setX((8 - fullFifoSize + j) * Constants.CELL_SIZE + Constants.CELL_OFFSET_1)
                            .setY(6 * Constants.CELL_SIZE + Constants.CELL_OFFSET_MINUS_DIV_11)
                            .setAnchor(.5)
                            .setZIndex(2);

                        car = new Car(carId + 10000, carSize, carPrio, dir, turn, carSprite, 8 - fullFifoSize + j, 6 ,carPassengers);
                        car.setOffsetX(Constants.CELL_OFFSET_1);
                        car.setOffsetY(Constants.CELL_OFFSET_MINUS_DIV_11);
                        break;
                }

                if (remainToActive > 0){
                    car.setActive();
                }
                remainToActive--;

                cars.put(car.getId(), car);
                lineFifos[i].add(car);
                //System.err.println(lineFifos[i]);



            }
        }
    }

    @Override
    public void gameTurn(int turn) {
        //gameManager.getPlayer().sendInputLine(fishPosition.toString());

        updateView();

        for (TrafficLight t : feux.values()){
            gameManager.getPlayer().sendInputLine(t.toString());
        }

        for (Car c : cars.values().stream().filter(Car::isActive).collect(Collectors.toList())){
            System.err.println(c);
            gameManager.getPlayer().sendInputLine(c.toString());
        }

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
                case "V_RED" : feux.get("N").setRed(); feux.get("S").setRed(); break;
                case "H_RED" : feux.get("E").setRed(); feux.get("W").setRed(); break;
                case "V_GREEN" : feux.get("E").setRed(); feux.get("W").setRed(); feux.get("N").setGreen(); feux.get("S").setGreen(); break;
                case "H_GREEN" : feux.get("N").setRed(); feux.get("S").setRed(); feux.get("E").setGreen(); feux.get("W").setGreen(); break;

            }

        } catch (TimeoutException e) {
            gameManager.loseGame("Timeout!");
        }
    }

    @Override
    public void onEnd() {
        //gameManager.putMetadata("eggs", String.valueOf(eggsCollected));
    }

    private void updateView() {
        feux.values().forEach(f -> f.updateEtat());

        cars.values().stream()
                //.peek(p -> System.err.println(p + " " + p.getSprite() + " " + p.getOffsetX()))
                .filter(a -> a != null && a.getSprite() != null && a.canMoveAndUpdate())
                .forEach(c -> c.getSprite().setX(c.updatePos(true)[0] * Constants.CELL_SIZE + c.getOffsetX(), Curve.LINEAR)
                .setY(c.updatePos(false)[1] * Constants.CELL_SIZE + c.getOffsetY(), Curve.LINEAR));

        scoreText.setText(newScore());

    }

    private String newScore(){
        int oldScore = score;

        score += Car.scorePlus() - Car.scoreMoins();

        return ""+score;
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


    public static Map<String, TrafficLight> getFeux() {
        return feux;
    }

}

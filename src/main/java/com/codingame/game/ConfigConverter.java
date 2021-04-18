package com.codingame.game;

import com.fasterxml.jackson.annotation.*;
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

    static public String convertJsonToTestIn(String jsonUrl){


        Path path = Paths.get(jsonUrl);
        File file = path.toFile();
        String read = "";

        try {
            read = Files.readAllLines(path).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        GameModel gameModel = null;
        try {
            gameModel = objectMapper.readValue(file, GameModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameModel.toString();
    }
}

@JsonIgnoreProperties({ "comment_01", "comment_02", "comment_03", "mask" })
class GameModel{
    private int directionsCount;
    private List<String> directions;
    private int trafficLigthsCount;
    private List<TrafficLightModel> trafficLights;
    private int fifoCounts;
    private int fifoSize;
    private List<LineFifoModel> fifos;

    public int getDirectionsCount() {
        return directionsCount;
    }

    public void setDirectionsCount(int directionsCount) {
        this.directionsCount = directionsCount;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public int getTrafficLigthsCount() {
        return trafficLigthsCount;
    }

    public void setTrafficLigthsCount(int trafficLigthsCount) {
        this.trafficLigthsCount = trafficLigthsCount;
    }

    public List<TrafficLightModel> getTrafficLights() {
        return trafficLights;
    }

    public void setTrafficLights(List<TrafficLightModel> trafficLights) {
        this.trafficLights = trafficLights;
    }

    public int getFifoCounts() {
        return fifoCounts;
    }

    public void setFifoCounts(int fifoCounts) {
        this.fifoCounts = fifoCounts;
    }

    public int getFifoSize() {
        return fifoSize;
    }

    public void setFifoSize(int fifoSize) {
        this.fifoSize = fifoSize;
    }

    public List<LineFifoModel> getFifos() {
        return fifos;
    }

    public void setFifos(List<LineFifoModel> fifos) {
        this.fifos = fifos;
    }

    @Override
    public String toString() {
        return directionsCount + FAUX_RETOUR +
               String.join(" ", directions) + FAUX_RETOUR +
               trafficLigthsCount + FAUX_RETOUR +
                    trafficLights.stream().map(TrafficLightModel::toString).collect(Collectors.joining(FAUX_RETOUR)) + FAUX_RETOUR +
               fifoCounts + FAUX_RETOUR +
               fifoSize + FAUX_RETOUR +
               String.join(FAUX_RETOUR, fifos.stream().map(LineFifoModel::toString).collect(Collectors.joining(FAUX_RETOUR)));
    }
}

class TrafficLightModel{
    private Integer lightId;
    private String lightDirTo;
    private String lightColor;
    private String lightStatus;
    private int lightDelayToListen;
    private String nextLightColor;
    private int delayToNextColor;

    public Integer getLightId() {
        return lightId;
    }

    public void setLightId(Integer lightId) {
        this.lightId = lightId;
    }

    public String getLightDirTo() {
        return lightDirTo;
    }

    public void setLightDirTo(String lightDirTo) {
        this.lightDirTo = lightDirTo;
    }

    public String getLightColor() {
        return lightColor;
    }

    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    public String getLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(String lightStatus) {
        this.lightStatus = lightStatus;
    }

    public int getLightDelayToListen() {
        return lightDelayToListen;
    }

    public void setLightDelayToListen(int lightDelayToListen) {
        this.lightDelayToListen = lightDelayToListen;
    }

    public int getDelayToNextColor() {
        return delayToNextColor;
    }

    public void setDelayToNextColor(int delayToNextColor) {
        this.delayToNextColor = delayToNextColor;
    }

    public String getNextLightColor() {
        return nextLightColor;
    }

    public void setNextLightColor(String nextLightColor) {
        this.nextLightColor = nextLightColor;
    }

    @Override
    public String toString() {
        return lightId + " " +
               lightDirTo + ' ' +
               lightColor + ' ' +
               lightStatus + ' ' +
               lightDelayToListen + " " +
               nextLightColor + ' ' +
               delayToNextColor;
    }
}

class LineFifoModel{
    private String fifoDirectionTo;
    private int fifoLimitSize;
    private List<CarModel> cars;

    public String getFifoDirectionTo() {
        return fifoDirectionTo;
    }

    public void setFifoDirectionTo(String fifoDirectionTo) {
        this.fifoDirectionTo = fifoDirectionTo;
    }

    public int getFifoLimitSize() {
        return fifoLimitSize;
    }

    public void setFifoLimitSize(int fifoLimitSize) {
        this.fifoLimitSize = fifoLimitSize;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return fifoDirectionTo + ' ' +
               fifoLimitSize + " " +
               String.join(FAUX_RETOUR, cars.stream().map(CarModel::toString).collect(Collectors.joining(FAUX_RETOUR)));
    }
}

class CarModel{
    private int carId;
    private String carDir;
    private String carTurn;
    private boolean carTurnVisible;
    private int carSize;
    private int carPassengers;
    private int carPriority;
    private int carPoints;
    private int carPenalty;
    private int carCellPosition;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarDir() {
        return carDir;
    }

    public void setCarDir(String carDir) {
        this.carDir = carDir;
    }

    public String getCarTurn() {
        return carTurn;
    }

    public void setCarTurn(String carTurn) {
        this.carTurn = carTurn;
    }

    public boolean isCarTurnVisible() {
        return carTurnVisible;
    }

    public void setCarTurnVisible(boolean carTurnVisible) {
        this.carTurnVisible = carTurnVisible;
    }

    public int getCarSize() {
        return carSize;
    }

    public void setCarSize(int carSize) {
        this.carSize = carSize;
    }

    public int getCarPassengers() {
        return carPassengers;
    }

    public void setCarPassengers(int carPassengers) {
        this.carPassengers = carPassengers;
    }

    public int getCarPriority() {
        return carPriority;
    }

    public void setCarPriority(int carPriority) {
        this.carPriority = carPriority;
    }

    public int getCarPoints() {
        return carPoints;
    }

    public void setCarPoints(int carPoints) {
        this.carPoints = carPoints;
    }

    public int getCarPenalty() {
        return carPenalty;
    }

    public void setCarPenalty(int carPenalty) {
        this.carPenalty = carPenalty;
    }

    public int getCarCellPosition() {
        return carCellPosition;
    }

    public void setCarCellPosition(int carCellPosition) {
        this.carCellPosition = carCellPosition;
    }

    @Override
    public String toString() {
        return carId + " " +
               carDir + " " +
               carTurn + " " +
               carTurnVisible + " " +
               carSize + " " +
               carPassengers + " " +
               carPriority + " " +
               carPoints + " " +
               carPenalty + " " +
               carCellPosition;
    }
}
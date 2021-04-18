package com.codingame.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

import static com.codingame.game.Constants.FAUX_RETOUR;

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

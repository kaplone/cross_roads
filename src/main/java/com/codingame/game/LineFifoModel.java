package com.codingame.game;

import java.util.List;
import java.util.stream.Collectors;

import static com.codingame.game.Constants.FAUX_RETOUR;

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
        return fifoLimitSize + " " +
                String.join(FAUX_RETOUR, cars.stream().map(CarModel::toString).collect(Collectors.joining(" ")));
    }
}

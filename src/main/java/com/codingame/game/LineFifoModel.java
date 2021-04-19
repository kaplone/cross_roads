package com.codingame.game;

import java.util.List;
import java.util.stream.Collectors;

import static com.codingame.game.Constants.FAUX_RETOUR;

class LineFifoModel{
    private String fifoDirectionTo;
    private int fifoSize;
    private int fifoLimitSizeDrawable;
    private List<CarModel> cars;

    public String getFifoDirectionTo() {
        return fifoDirectionTo;
    }

    public void setFifoDirectionTo(String fifoDirectionTo) {
        this.fifoDirectionTo = fifoDirectionTo;
    }

    public int getFifoSize() {
        return fifoSize;
    }

    public void setFifoSize(int fifoSize) {
        this.fifoSize = fifoSize;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }

    public int getFifoLimitSizeDrawable() {
        return fifoLimitSizeDrawable;
    }

    public void setFifoLimitSizeDrawable(int fifoLimitSizeDrawable) {
        this.fifoLimitSizeDrawable = fifoLimitSizeDrawable;
    }

    @Override
    public String toString() {
        return fifoSize + " " +
                fifoLimitSizeDrawable + " " +
                String.join(FAUX_RETOUR, cars.stream().map(CarModel::toString).collect(Collectors.joining(" ")));
    }
}

package com.codingame.game;

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

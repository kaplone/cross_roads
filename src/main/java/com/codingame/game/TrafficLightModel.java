package com.codingame.game;

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

package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;

public class TrafficLight {
    private Integer id;
    private Sprite sprite;
    private LightColor color;
    private boolean changeEtat;
    private LightColor nextColor;
    private Status status;
    private int delayToListen;
    private int delayToNextColor;
    private String dir;
    private String nextImage;

    private static int DELAY_TO_RED = 6;
    private static int DELAY_TO_GREEN = 7;

    public TrafficLight(Integer id, Sprite sprite, String dir, LightColor lightColor, Status status, Integer delayToNextColor, Integer delayToListen, LightColor nextColor ) {
        this.id = id;
        this.sprite = sprite;
        this.color = lightColor;
        this.nextColor = nextColor;
        this.dir = dir;
        this.changeEtat = false;
        this.delayToListen = delayToListen;
        this.delayToNextColor = delayToNextColor;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setRed(){
        //System.err.println("setRed::");
        if (!changeEtat && color == LightColor.GREEN){
            delayToListen = DELAY_TO_RED;
            delayToNextColor = DELAY_TO_RED;
            changeEtat = true;
            nextColor = LightColor.RED;
            nextImage = Constants.FEU_ROUGE;
            //System.err.println("Appel -> red");
        }
    }

    public void setGreen(){
        //System.err.println("setGreen::");
        if (!changeEtat && color == LightColor.RED){
            delayToListen = DELAY_TO_GREEN;
            delayToNextColor = DELAY_TO_GREEN;
            changeEtat = true;
            nextColor = LightColor.GREEN;
            nextImage = Constants.FEU_VERT;
            //System.err.println("Appel -> green");
        }
    }

    public String getDir() {
        return dir;
    }

    public void updateEtat(){
        if (changeEtat && delayToNextColor == 0){
            color = nextColor;
            sprite.setImage(nextImage);
            changeEtat = false;
            nextColor = null;
            nextImage = null;
        }
        else if (changeEtat&& delayToNextColor == 5 && nextColor == LightColor.RED){
            sprite.setImage(Constants.FEU_ORANGE);
        }
        else if (changeEtat&& delayToNextColor == 4 && nextColor == LightColor.RED){
            color = nextColor;
        }
        else if (changeEtat&& delayToNextColor == 3 && nextColor == LightColor.RED){
            sprite.setImage(nextImage);
            //System.err.println("-> orange");
        }
        else if (changeEtat&& delayToNextColor == 2 && nextColor == LightColor.GREEN){
            sprite.setImage(Constants.FEU_VERT);
        }

        delayToNextColor--;
        delayToListen--;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LightColor getColor() {
        return color;
    }

    public void setColor(LightColor color) {
        this.color = color;
    }

    public boolean isChangeEtat() {
        return changeEtat;
    }

    public void setChangeEtat(boolean changeEtat) {
        this.changeEtat = changeEtat;
    }

    public LightColor getNextColor() {
        return nextColor;
    }

    public void setNextColor(LightColor nextColor) {
        this.nextColor = nextColor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getDelayToListen() {
        return delayToListen;
    }

    public void setDelayToListen(int delayToListen) {
        this.delayToListen = delayToListen;
    }

    public int getDelayToNextColor() {
        return delayToNextColor;
    }

    public void setDelayToNextColor(int delayToNextColor) {
        this.delayToNextColor = delayToNextColor;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getNextImage() {
        return nextImage;
    }

    public void setNextImage(String nextImage) {
        this.nextImage = nextImage;
    }

    @Override
    public String toString(){
        return getId() + " " + getDir() + " " + getColor() + " " +  getStatus() + " " + getDelayToListen() + " " + getNextColor() + " " + getDelayToNextColor();
    }
}

package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;
import com.sun.webkit.graphics.Ref;

import java.util.Objects;
import java.util.stream.Collectors;

public class Car {
    private Integer id;
    private String dir;
    private String turn;
    private Integer size;
    private boolean prio;
    private Sprite sprite;
    private Integer x;
    private Integer y;
    private Integer score;
    private int offsetX;
    private int offsetY;
    private int turnStop;
    private int passengers;
    private int points;
    private int penalty;
    private boolean visible;
    private boolean done;


    public Car(Integer id, Integer size, Integer prio, String dir, String turn, Sprite sprite, Integer x, Integer y, Integer passengers) {
        this.id = id;
        this.dir = dir;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.size = size;
        this.prio = prio == 1;
        this.turn = turn;
        this.score = 0;
        this.passengers = passengers;
        this.points = size * 20 + (1 + passengers) * (prio == 1 ? 30 : 10);
        this.penalty = 0;
        this.visible = true;
        this.done = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int[] updatePos(Integer x, Integer y){
        //System.err.print(Arrays.toString(new int[] {this.x, this.y}) + " -> ");
        this.x += x;
        this.y += y;
        //System.err.print(Arrays.toString(new int[] {this.x, this.y}));
        return new int[] {this.x, this.y};
    }

    public int[] updatePos(boolean evaluate){
        if (evaluate){
            switch (this.dir){
                case "N" : int[] move_u = updatePos(0, -1); score = (move_u[1] < 6) ? 1 : 0; return move_u;
                case "S" : int[] move_d = updatePos(0, 1); score = (move_d[1] > 4) ? 1 : 0; return move_d;
                case "W" : int[] move_l = updatePos(-1, 0); score = (move_l[0] < 10) ? 1 : 0; return move_l;
                case "E" : int[] move_r = updatePos(+1, 0); score = (move_r[0] > 8) ? 1 : 0; return move_r;
                default: return null;
            }
        }
        else {
            return new int[] {this.x, this.y};
        }
    }

    public boolean canMoveAndUpdate(){
        boolean canMove = canMove();

        if (isActive()){
            if (!canMove){
                if (penalty == 0 && points > 0){
                    points -= 1 + passengers;
                }
                else {
                    penalty += 1 + passengers;
                }

            }
            else {
                if (penalty == 0){
                    points += passengers;
                }
            }
        }

        return canMove;
    }

    public boolean canMove(){

        //System.err.println(id  + " " + dir + " " + isSousFeu() + " " + lineIsGreen());

        if (isSousFeu() && !lineIsGreen()){
            return false;
        }

        switch(this.getDir()){
            case "N" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .noneMatch(c -> c.getDir().equals(this.getDir()) && c.getX().equals(this.getX()) && c.getY() == this.getY() - 1);
            case "S" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .noneMatch(c -> c.getDir().equals(this.getDir()) && c.getX().equals(this.getX()) && c.getY() == this.getY() + 1);
            case "W" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .noneMatch(c -> c.getDir().equals(this.getDir()) && c.getX() == this.getX() - 1 && c.getY().equals(this.getY()));
            case "E" : return
                    Referee.getCars()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .noneMatch(c -> c.getDir().equals(this.getDir()) && c.getX() == this.getX() + 1 && c.getY().equals(this.getY()));
            default: return false;
        }
    }

    public boolean lineIsGreen(){
        return Referee.getFeux().get(dir).getColor() == LightColor.GREEN;
    }

    public boolean isSousFeu(){
        switch (dir){
            case "N" :
            case "S" : return Constants.LIBERATIONS_IN_CROSS.get(dir).equals(getY());
            case "W" :
            case "E" : return Constants.LIBERATIONS_IN_CROSS.get(dir).equals(getX());
            default: return false;
        }
    }

    public static int scorePlus(){
        int res = 0;
        for(Car c : Referee.getCars().values().stream().filter(Car::isVisible).collect(Collectors.toList())){
            switch (c.getDir()){
                case "N" :
                case "S" : if(Constants.LIBERATIONS_OUT_CROSS.get(c.getDir()).equals(c.getY())){
                    c.setInactive();
                    res += Math.max(c.points, 0);
                    Referee.getCars().values().stream().filter(a -> a.getDir().equals(c.getDir()) && a.isReady()).findFirst().ifPresent(Car::setActive);
                }
                break;
                case "W" :
                case "E" : if(Constants.LIBERATIONS_OUT_CROSS.get(c.getDir()).equals(c.getX())) {
                    c.setInactive();
                    res += Math.max(c.points, 0);
                    Referee.getCars().values().stream().filter(a -> a.getDir().equals(c.getDir()) && a.isReady()).findFirst().ifPresent(Car::setActive);
                }
                default: ;
            }
        }

        return res;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isPrio() {
        return prio;
    }

    public void setPrio(boolean prio) {
        this.prio = prio;
    }

    public boolean isScored(){
        return score > 0;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public Integer getScore() {
        return score;
    }

    public int getTimeWait(Integer turn){
        return getTurnStop() != null ? turn - getTurnStop() : 0;
    }

    public Integer getTurnStop() {
        return turnStop;
    }

    public void setTurnStop(int turnStop) {
        this.turnStop = turnStop;
    }

    public int getPassengers() {
        return passengers;
    }

    public int getPoints() {
        return points;
    }

    public int getPenalty() {
        return penalty;
    }

    public Boolean isVisible(){
        return true;
    }

    public boolean isActive(){
        return visible && !done;
    }



    public void setInactive(){
        visible = false;
        done = true;
    }

    public void setActive(){
        visible = true;
        done = false;
    }

    public boolean isReady(){
        return !visible && !done;
    }

    @Override
    public String toString(){
        return getId() +  " " + getDir() + " " + getTurn() + " " + getSize() + " " + getPassengers() + " " + getPoints() + " " + getPenalty();
    }
}

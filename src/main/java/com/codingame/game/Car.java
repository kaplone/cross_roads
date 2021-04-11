package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;

import java.util.Objects;

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


    public Car(Integer id, Integer size, Integer prio, String dir, String turn, Sprite sprite, Integer x, Integer y) {
        this.id = id;
        this.dir = dir;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.size = size;
        this.prio = prio == 1;
        this.turn = turn;
        this.score = 0;
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
                case "U" : int[] move_u = updatePos(0, -1); score = (move_u[1] < 6) ? 1 : 0; return move_u;
                case "D" : int[] move_d = updatePos(0, 1); score = (move_d[1] > 4) ? 1 : 0; return move_d;
                case "L" : int[] move_l = updatePos(-1, 0); score = (move_l[0] < 10) ? 1 : 0; return move_l;
                case "R" : int[] move_r = updatePos(+1, 0); score = (move_r[0] > 8) ? 1 : 0; return move_r;
                default: return null;
            }
        }
        else {
            return new int[] {this.x, this.y};
        }
    }

    public boolean canMoveAndUpdate(){
        boolean canMove = canMove();

        if (!canMove){
            if (penalty == 0 && points > 0){
                points -= 1 + passengers;
            }
            else {
                penalty += 1 + passengers;
            }

        }
        else {
            if (penalty == 0 && points > 0){
                points += passengers;
            }
        }

        return canMove;
    }

    public boolean canMove(){

        if (isSousFeu() && !lineIsGreen()){
            return false;
        }

        switch(this.getDir()){
            case "U" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .noneMatch(c -> c.getDir().equals(this.getDir()) && c.getX().equals(this.getX()) && c.getY() == this.getY() - 1);
            case "D" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .peek(p -> {
                        if (p.getId() == 303) {
                            //System.err.println((p.getY() + 1) + " " + this.getY());
                        }})
                    .noneMatch(c -> c.getDir().equals(this.getDir()) && c.getX().equals(this.getX()) && c.getY() == this.getY() + 1);
            case "L" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .peek(p -> {
                        if (p.getId() == 203 && p.getX() + 1 == this.getX()) {
                            //System.err.println(p.getId() + ":" + (p.getX() + 1) + " " + this.getId() + ":" + this.getX());
                        }})
                    .noneMatch(c -> c.getDir().equals(this.getDir()) && c.getX() == this.getX() - 1 && c.getY().equals(this.getY()));
            case "R" : return
                    Referee.getCars()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .noneMatch(c -> c.getDir().equals(this.getDir()) && c.getX() == this.getX() + 1 && c.getY().equals(this.getY()));
            default: return false;
        }
    }

    public boolean lineIsGreen(){
        return Referee.getFeux().get(dir).getEtat().equals("V");
    }

    public boolean isSousFeu(){
        switch (dir){
            case "U" :
            case "D" : return Constants.LIBERATIONS_IN_CROSS.get(dir).equals(getY());
            case "L" :
            case "R" : return Constants.LIBERATIONS_IN_CROSS.get(dir).equals(getX());
            default: return false;
        }
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
}

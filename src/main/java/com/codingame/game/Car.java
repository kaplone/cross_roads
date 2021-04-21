package com.codingame.game;

import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class Car {
    private Integer id;
    private String dir;
    private String oldDir;
    private String turn;
    private Boolean turnVisible;
    private Integer size;
    private boolean prio;
    private Sprite spriteCar;
    private Sprite spriteBox;
    private Text spritePoints;
    private Text spritePointsPop;
    private Group carGroup;
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
    private boolean drawable;
    private boolean done;
    private int turnInLoop;
    private boolean repriseTurn;
    private boolean newStatutReprise;


    public Car(Integer id, Integer size, Integer prio, String dir, String turn, Sprite spriteCar, Integer x, Integer y, Integer passengers, boolean drawable) {
        this.id = id;
        this.dir = dir;
        this.oldDir = dir;
        this.spriteCar = spriteCar;
        this.x = x;
        this.y = y;
        this.size = size;
        this.prio = prio == 1;
        this.turn = turn;
        this.score = 0;
        this.passengers = passengers;
        this.points = size * 20 + (1 + passengers) * (prio == 1 ? 30 : 10);
        this.penalty = 0;
        this.visible = drawable;
        this.drawable = drawable;
        this.done = false;
        this.turnInLoop = 0;
        this.repriseTurn = false;
        this.newStatutReprise = false;
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

    public Sprite getSpriteCar() {
        return spriteCar;
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
                case "N" : int[] move_u = updatePos(0, -1);
                score = (move_u[1] < 5) ? 1 : 0;
                drawable = move_u[1] >= -1;
                if (!drawable){
                    setInactive();
                    Referee.getCars().values()
                            .stream()
                            .sorted(Comparator.comparing(Car::getId).reversed())
                            .filter(car -> car.getDir().equals("N") && !car.isDrawable() && !car.isScored())
                            .findFirst()
                            .ifPresent(a -> a.setDrawable(true));
                }

                return move_u;
                case "S" : int[] move_d = updatePos(0, 1);
                score = (move_d[1] > 5) ? 1 : 0;
                drawable = move_d[1] <= 11;
                if (!drawable){
                    setInactive();
                    Referee.getCars().values()
                            .stream()
                            .sorted(Comparator.comparing(Car::getId).reversed())
                            .filter(car -> car.getDir().equals("S") && !car.isDrawable() && !car.isScored())
                            .findFirst()
                            .ifPresent(a -> a.setDrawable(true));
                }

                return move_d;
                case "W" : int[] move_l = updatePos(-1, 0);
                score = (move_l[0] < 9) ? 1 : 0;
                drawable = move_l[0] >= -1;
                if (!drawable){
                    setInactive();
                    Referee.getCars().values()
                            .stream()
                            .sorted(Comparator.comparing(Car::getId).reversed())
                            .filter(car -> car.getDir().equals("W") && !car.isDrawable() && !car.isScored())
                            .findFirst()
                            .ifPresent(a -> a.setDrawable(true));
                }

                return move_l;
                case "E" : int[] move_r = updatePos(+1, 0);
                score = (move_r[0] > 9) ? 1 : 0;
                drawable = move_r[0] <= 19;
                if (!drawable){
                    setInactive();
                    Referee.getCars().values()
                            .stream()
                            .sorted(Comparator.comparing(Car::getId).reversed())
                            .filter(car -> car.getDir().equals("E") && !car.isDrawable() && !car.isScored())
                            .findFirst()
                            .ifPresent(a -> a.setDrawable(true));
                }

                return move_r;
                default: return null;
            }
        }
        else {
            return new int[] {this.x, this.y};
        }
    }

    public boolean canMoveAndUpdate(){
        boolean canMove = canMove();

        if (canMove){
            canMove = !hasToTurn();
        }

        if (getId() == 17){
            System.err.println(canMove);
        }

        if (isActive()){
            turnInLoop++;
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

    private boolean hasToTurn(){
//        if (getTurn().equals(">")){
//            System.err.println("getY() " + getY());
//        }

        boolean res = false;

        if (getId() == 17){
            System.err.println(getY() + " " + getDir());
        }

        switch (this.dir) {
            case "N":

                // Process turn right = N -> E
                if (getY() == 6 && getTurn().equals(">")) {
                    System.err.println(id + " Turn N -> E");
                    setDir("E");
                    setTurn("^");
                    spriteBox.setAlpha(0, Curve.LINEAR);
                    spriteCar.setRotation(Math.PI / 2)
                            .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET_1_E)
                            .setY(6 * Constants.CELL_SIZE + Constants.CELL_OFFSET_MINUS_DIV_11);
                    this.setX(9);
                    this.setY(6);
                    setOffsetX(Constants.CELL_OFFSET_1_E);
                    setOffsetY(Constants.CELL_OFFSET_MINUS_DIV_11);

                // Start turn left = N -> W
                } else if (getY() == 5 && getTurn().equals("<")) {
                    System.err.println(id + " Turn N -> W");
                    setDir("W");
                    setTurn("^");
                    repriseTurn = true;
                    newStatutReprise = true;
                    if (lineIsRed("S")) {
                        spriteBox.setAlpha(0, Curve.LINEAR);
                        spriteCar.setRotation(3 * Math.PI / 2)
                                .setX((9) * Constants.CELL_SIZE + Constants.CELL_OFFSET_1_W)
                                .setY(5 * Constants.CELL_SIZE + Constants.CELL_OFFSET_DIV_4);
                        this.setX(9);
                        this.setY(5);
                        setOffsetX(Constants.CELL_OFFSET_1_W);
                        setOffsetY(Constants.CELL_OFFSET_DIV_4);

                    }
                    res = true;
                }

                // End turn left = E -> N
                else if("N".equals(dir) && repriseTurn) {
                    System.err.println(id + " Reprise Turn E -> N");
                    if (lineIsRed("W")) {
                        spriteBox.setAlpha(0, Curve.LINEAR);
                        spriteCar.setRotation(0)
                                .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                                .setY((5) * Constants.CELL_SIZE + Constants.CELL_OFFSET);
                        this.setX(9);
                        this.setY(5);
                        setOffsetX(Constants.CELL_OFFSET);
                        setOffsetY(Constants.CELL_OFFSET);
                        repriseTurn = false;
                    }
                }

            break;
            case "E" :
                // Process turn right = E -> S
                if (getX() == 8 && getTurn().equals(">")) {
                    System.err.println(id + " Turn E -> S");
                    setDir("S");
                    setTurn("^");
                    spriteBox.setAlpha(0, Curve.LINEAR);
                    spriteCar.setRotation(Math.PI)
                            .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET_0)
                            .setY(5 * Constants.CELL_SIZE + Constants.CELL_OFFSET_1);
                    this.setX(9);
                    this.setY(5);
                    setOffsetX(Constants.CELL_OFFSET_0);
                    setOffsetY(Constants.CELL_OFFSET_1);
                }

                // Start turn left = E -> N
                else if (getX() == 9 && getTurn().equals("<")) {
                    System.err.println(id + " Turn E -> N");
                    setDir("N");
                    setTurn("^");
                    repriseTurn = true;
                    newStatutReprise = true;
                    if (lineIsRed("W")) {
                        spriteBox.setAlpha(0, Curve.LINEAR);
                        spriteCar.setRotation(0)
                                .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                                .setY((5) * Constants.CELL_SIZE + Constants.CELL_OFFSET);
                        this.setX(9);
                        this.setY(5);
                        setOffsetX(Constants.CELL_OFFSET);
                        setOffsetY(Constants.CELL_OFFSET);

                    }
                    res = true;
                }

                // End turn left = S -> E
                else if("E".equals(dir) && repriseTurn) {
                    System.err.println(id + " Reprise Turn S -> E");
                    if (lineIsRed("N")) {
                        spriteCar.setRotation(Math.PI / 2)
                                .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET_1_E)
                                .setY(6 * Constants.CELL_SIZE + Constants.CELL_OFFSET_MINUS_DIV_11);
                        this.setX(9);
                        this.setY(6);
                        setOffsetX(Constants.CELL_OFFSET_1_E);
                        setOffsetY(Constants.CELL_OFFSET_MINUS_DIV_11);
                        repriseTurn = false;
                    }

                }

                break;
            case "W" :

                // Process turn right = W -> N
                if (getX() == 10 && getTurn().equals(">")) {
                    System.err.println(id + " Turn W -> N");
                    setDir("N");
                    setTurn("^");
                    spriteBox.setAlpha(0, Curve.LINEAR);
                    spriteCar.setRotation(0)
                            .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET)
                            .setY(5 * Constants.CELL_SIZE + Constants.CELL_OFFSET);
                    this.setX(9);
                    this.setY(5);
                    setOffsetX(Constants.CELL_OFFSET);
                    setOffsetY(Constants.CELL_OFFSET);
                }

                // Start turn left = W -> S
                else if (getX() == 9 && getTurn().equals("<")) {
                    System.err.println(id + " Turn W -> S");
                    setDir("S");
                    setTurn("^");
                    repriseTurn = true;
                    newStatutReprise = true;
                    if (lineIsRed("E")) {
                        spriteBox.setAlpha(0, Curve.LINEAR);
                        spriteCar.setRotation(Math.PI)
                                .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET_0)
                                .setY(5 * Constants.CELL_SIZE + Constants.CELL_OFFSET_1);
                        this.setX(9);
                        this.setY(5);
                        setOffsetX(Constants.CELL_OFFSET_0);
                        setOffsetY(Constants.CELL_OFFSET_1);

                    }
                    res = true;
                }

            // End turn left = N -> W
            else if("W".equals(dir) && repriseTurn) {
                System.err.println(id + " Reprise Turn N -> W");
                if (lineIsRed("S")) {
                    spriteBox.setAlpha(0, Curve.LINEAR);
                    spriteCar.setRotation(3 * Math.PI / 2)
                            .setX((9) * Constants.CELL_SIZE + Constants.CELL_OFFSET_1_W)
                            .setY(5 * Constants.CELL_SIZE + Constants.CELL_OFFSET_DIV_4);
                    this.setX(9);
                    this.setY(5);
                    setOffsetX(Constants.CELL_OFFSET_1_W);
                    setOffsetY(Constants.CELL_OFFSET_DIV_4);
                    repriseTurn = false;
                }
            }
            break;

            case "S" :

                // Process turn right = S -> W
                if (getY() == 4 && getTurn().equals(">")) {
                    System.err.println(id + " Turn S -> W");
                    setDir("W");
                    setTurn("^");
                    spriteBox.setAlpha(0, Curve.LINEAR);
                    spriteCar.setRotation(3 * Math.PI / 2)
                            .setX(8 * Constants.CELL_SIZE + Constants.CELL_OFFSET_1_W)
                            .setY(5 * Constants.CELL_SIZE + Constants.CELL_OFFSET_DIV_4);
                    this.setX(8);
                    this.setY(5);
                    setOffsetX(Constants.CELL_OFFSET_1_W);
                    setOffsetY(Constants.CELL_OFFSET_DIV_4);

                    // Start turn left = S -> E
                } else if (getY() == 5 && getTurn().equals("<")) {
                    System.err.println(id + " Turn S -> E");
                    setDir("E");
                    setTurn("^");
                    repriseTurn = true;
                    newStatutReprise = true;
                    if (lineIsRed("N")) {
                        spriteBox.setAlpha(0, Curve.LINEAR);
                        spriteCar.setRotation(Math.PI / 2)
                                .setX((9) * Constants.CELL_SIZE + Constants.CELL_OFFSET_1_E)
                                .setY(6 * Constants.CELL_SIZE + Constants.CELL_OFFSET_MINUS_DIV_11);
                        this.setX(9);
                        this.setY(6);
                        setOffsetX(Constants.CELL_OFFSET_1_E);
                        setOffsetY(Constants.CELL_OFFSET_MINUS_DIV_11);

                    }
                    res = true;
                }

                // End turn left = W -> S
                else if("S".equals(dir) && repriseTurn) {
                    System.err.println(id + " Reprise Turn W -> S");
                    if (lineIsRed("E")) {
                        spriteBox.setAlpha(0, Curve.LINEAR);
                        spriteCar.setRotation(Math.PI)
                                .setX(9 * Constants.CELL_SIZE + Constants.CELL_OFFSET_0)
                                .setY(4* Constants.CELL_SIZE + Constants.CELL_OFFSET_1);
                        this.setX(9);
                        this.setY(5);
                        setOffsetX(Constants.CELL_OFFSET_0);
                        setOffsetY(Constants.CELL_OFFSET_1);
                        repriseTurn = false;
                    }
                }

                break;

        }
        return res;
    }

    public boolean canMove(){

        //System.err.println(id  + " " + dir + " " + isSousFeu() + " " + lineIsGreen());

        //hasToTurn();

        if (isSousFeu() && !lineIsGreen()){
            return false;
        }

//        if (this.getId() == 108 || this.getId() == 109){
//            System.err.println(getX() +":" + getY() + " = " +  getDir());
//        }

        switch(this.getDir()){
            case "N" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(obj -> Objects.nonNull(obj) && obj.isDrawable())
                    .noneMatch(c -> (c.getDir().equals(this.getDir()) && c.getX().equals(this.getX()) && c.getY() == this.getY() - 1))
                    && !(getY() == 6 && !Referee.isLibreInCross("N", getId()))
                    && !(repriseTurn && !lineIsRed("W"));
            case "S" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(obj -> Objects.nonNull(obj) && obj.isDrawable())
                    .noneMatch(c -> (c.getDir().equals(this.getDir()) && c.getX().equals(this.getX()) && c.getY() == this.getY() + 1))
                    && !(getY() == 4 && !Referee.isLibreInCross("S", getId()))
                    && !(repriseTurn && !lineIsRed("E"));
            case "W" : return Referee.getCars()
                    .values()
                    .stream()
                    .filter(obj -> Objects.nonNull(obj) && obj.isDrawable())
                    .noneMatch(c -> (c.getDir().equals(this.getDir()) && c.getX() == this.getX() - 1 && c.getY().equals(this.getY())))
                    && !(getX() == 9 && !Referee.isLibreInCross("W", getId()))
                    && !(repriseTurn && !lineIsRed("S"));
            case "E" : return
                    Referee.getCars()
                    .values()
                    .stream()
                    .filter(obj -> Objects.nonNull(obj) && obj.isDrawable())
                    .noneMatch(c -> (c.getDir().equals(this.getDir()) && c.getX() == this.getX() + 1 && c.getY().equals(this.getY())))
                    && !(getX() == 8 + (repriseTurn ? 1 : 0) && !Referee.isLibreInCross("E", getId()))
                    && !(repriseTurn && !lineIsRed("N"));
            default: return false;
        }
    }

    public boolean lineIsGreen(){
        return Referee.getFeux().get(dir).getColor() == LightColor.GREEN;
    }

    public boolean lineIsRed(String dir_){
        return Referee.getFeux().get(dir_).getColor() == LightColor.RED && !Referee.getFeux().get(dir_).isChangeEtat();
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
        for(Car c : Referee.getCars().values().stream().filter(Car::isActive).collect(Collectors.toList())){
            switch (c.getDir()){
                case "N" :
                case "S" :
                    //System.err.println("Liberation = " + Constants.LIBERATIONS_OUT_CROSS.get(c.getDir()) + " # c.getY() = " + c.getY());
                    if(Constants.LIBERATIONS_OUT_CROSS.get(c.getDir()).equals(c.getY())){
                    c.setInactive();
                    res += Math.max(c.points, 0);
                    Referee.getCars().values().stream().filter(a -> a.getDir().equals(c.getDir()) && a.isReady()).findFirst().ifPresent(Car::setActive);
                }
                break;
                case "W" :
                case "E" :
                    //System.err.println("Liberation = " + Constants.LIBERATIONS_OUT_CROSS.get(c.getDir()) + " # c.getX() = " + c.getX());
                    if(Constants.LIBERATIONS_OUT_CROSS.get(c.getDir()).equals(c.getX())) {
                    c.setInactive();
                    res += Math.max(c.points, 0);
                    Referee.getCars().values().stream().filter(a -> a.getDir().equals(c.getDir()) && a.isReady()).findFirst().ifPresent(Car::setActive);
                }
                default: ;
            }
        }
        //System.err.println("Score plus = " + res);
        return res;
    }

    public static int scoreMoins(){
        int res = 0;
        for(Car c : Referee.getCars().values().stream().filter(Car::isActive).collect(Collectors.toList())){
            res += Math.max(c.penalty, 0);
        }
        //System.err.println("Score moins = " + res);
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
        return visible;
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

    public Group getCarGroup() {
        return carGroup;
    }

    public void setCarGroup(Group carGroup) {
        this.carGroup = carGroup;
    }

    public void setSpriteCar(Sprite spriteCar) {
        this.spriteCar = spriteCar;
    }

    public Text getSpritePointsPop() {
        return spritePointsPop;
    }

    public void setSpritePointsPop(Text spritePointsPop) {
        this.spritePointsPop = spritePointsPop;
    }

    public Sprite getSpriteBox() {
        return spriteBox;
    }

    public void setSpriteBox(Sprite spriteBox) {
        this.spriteBox = spriteBox;
    }

    public Text getSpritePoints() {
        return spritePoints;
    }

    public int getTurnInLoop() {
        return turnInLoop;
    }

    public void setSpritePoints(Text spritePoints) {
        this.spritePoints = spritePoints;
    }

    public String getTurnVisible() {
        return turnVisible ? getTurn() : "^";
    }

    public boolean isReady(){
        return !visible && !done;
    }

    public void setNewStatutReprise(boolean b){
        newStatutReprise = false;
    }

    public boolean isRepriseTurn() {
        return repriseTurn;
    }

    public String getOldDir() {
        return oldDir;
    }

    public boolean isDrawable() {
        return drawable;
    }

    public void setDrawable(boolean drawable) {
        this.drawable = drawable;
        this.visible = drawable;
    }

    @Override
    public String toString(){
        return getId() +  " " + getDir() + " " + getTurn() + " " + getSize() + " " + getPassengers() + " " + getPoints() + " " + getPenalty();
    }
}

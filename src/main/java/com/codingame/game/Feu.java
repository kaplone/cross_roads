package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;

public class Feu {
    private Integer id;
    private Sprite sprite;
    private String etat;
    private boolean changeEtat;
    private String nextEtat;
    private int delai;
    private String dir;
    private String nextImage;

    public Feu(Integer id, Sprite sprite, String etat, String dir) {
        this.id = id;
        this.sprite = sprite;
        this.etat = etat;
        this.dir = dir;
        this.changeEtat = false;
        this.delai = 0;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setRed(){
        //System.err.println("setRed::");
        if (!changeEtat && etat.equals("V")){
            delai = 6;
            changeEtat = true;
            nextEtat = "R";
            nextImage = Constants.FEU_ROUGE;
            //System.err.println("Appel -> red");
        }
    }

    public void setGreen(){
        //System.err.println("setGreen::");
        if (!changeEtat && etat.equals("R")){
            delai = 7;
            changeEtat = true;
            nextEtat = "V";
            nextImage = Constants.FEU_VERT;
            //System.err.println("Appel -> green");
        }
    }

    public String getDir() {
        return dir;
    }

    public void updateEtat(){
        if (changeEtat && delai == 0){
            etat = nextEtat;
            sprite.setImage(nextImage);
            changeEtat = false;
            nextEtat = null;
            nextImage = null;
        }
        else if (changeEtat&& delai == 5 && "R".equals(nextEtat)){
            sprite.setImage(Constants.FEU_ORANGE);
        }
        else if (changeEtat&& delai == 4 && "R".equals(nextEtat)){
            etat = nextEtat;
        }
        else if (changeEtat&& delai == 3 && "R".equals(nextEtat)){
            sprite.setImage(nextImage);
            //System.err.println("-> orange");
        }
        else if (changeEtat&& delai == 2 && "V".equals(nextEtat)){
            sprite.setImage(Constants.FEU_VERT);
        }

        delai--;
    }
}

package com.escom.gameoflife;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class Animation extends AnimationTimer {
    GameAutomaton game;    
    Space currentEvolution;
    Space nextEvolution;
    GraphicsContext gc;
    long frames=0;
    long fw=30;
    public long number_evolutions=0;
    
    public Animation(GameAutomaton game, Space currentEvolution, Space nextEvolution, GraphicsContext g){
        this.game=game;       
        this.currentEvolution=currentEvolution;
        this.nextEvolution=nextEvolution;
        this.gc=g;        
    }
    
    @Override
    public void handle(long now) {        
        if(frames%fw==0){
            nextEvolution=game.getNextGeneration(currentEvolution);
            nextEvolution.drawSpace(gc);
            currentEvolution=nextEvolution;
            nextEvolution=null;
        }
        ++frames;
        ++number_evolutions;
    }
    
    public void decreaseSpeed(){
        if(fw<60)
            fw++;
    }
    
    public void increaseSpeed(){
        if(fw>1)
            fw--;
    }
    
    public Space getCurrentEvolution(){
        return currentEvolution;
    }

    
    
    
}

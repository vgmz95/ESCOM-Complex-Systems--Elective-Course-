package com.escom.bmltraffic;

//package BML;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Animation extends AnimationTimer {
    private final BMLAutomaton game;    
    private Space currentEvolution;
    private Space nextEvolution;
    private GraphicsContext gc;
    private long frames=0;
    private long fw=30;
    private long number_evolutions=0;
    private double factor=2;
    
    public Animation(BMLAutomaton game, Space currentEvolution, Space nextEvolution){
        this.game=game;       
        this.currentEvolution=currentEvolution;
        this.nextEvolution=nextEvolution;
        gc = new Canvas(currentEvolution.getXSize(),currentEvolution.getYSize()).getGraphicsContext2D();
        gc.getCanvas().setWidth(factor*(double)currentEvolution.getXSize());
        gc.getCanvas().setHeight(factor*(double)currentEvolution.getYSize());
        gc.scale(factor, factor);       
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
    
    public void increaseZoom(){
        if(factor<13){
            gc.scale(1/factor, 1/factor);
            factor+=1;        
            gc.scale(factor,factor);
            gc.getCanvas().setWidth(factor*(double)currentEvolution.getXSize());
            gc.getCanvas().setHeight(factor*(double)currentEvolution.getYSize());
        }
    }
    
    public void decreaseZoom(){
        if(factor>1){
            gc.scale(1/factor,1/factor);
            factor-=1;
            gc.scale(factor,factor);
            gc.getCanvas().setWidth(factor*(double)currentEvolution.getXSize());
            gc.getCanvas().setHeight(factor*(double)currentEvolution.getYSize());
        }
    }
    
    public void setZoom(double f){            
        gc.scale(1/factor,1/factor);               
        factor=f;
        if(factor>=1){
            gc.scale(factor,factor); 
            gc.getCanvas().setWidth(factor*(double)currentEvolution.getXSize());
            gc.getCanvas().setHeight(factor*(double)currentEvolution.getYSize());
        }
    }
    
    
    public Canvas getCanvas(){
        return gc.getCanvas();
    }

}

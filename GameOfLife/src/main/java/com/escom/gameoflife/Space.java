
package com.escom.gameoflife;

import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Space {
    private final boolean[][] array;
    
    public Space(short x,short y){
        array=new boolean[y][x];
    }
    
    short getYSize(){ return (short) array.length;}
    
    short getXSize(){ return (short) array[0].length;}
    
    int getTotalSize(){return this.getXSize()*this.getYSize();}
    
    boolean[][] getArray(){ return array;}
    
    short getValueAt(short x, short y){
        if(array[mod(y,this.getYSize())][mod(x,this.getXSize())])
            return 1;
        else
            return 0;
    }
    
    boolean getBooleanValueAt(short x, short y){
        return (array[mod(y,this.getYSize())][mod(x,this.getXSize())]);
    }
    
    short mod(short a, short b){
        short r = (short) (a % b);
        return  (short) (r < 0 ? r + b : r);
    }
    
    void setValueAt(short x, short y,boolean value){
        array[y][x]=value;        
    }
    
    void setValueAt(short x, short y,byte value){
        array[y][x] = (value==1);
    }
    
    void drawSpace(GraphicsContext gc){        
        for(short y=0;y<getYSize();y++){
            for(short x=0;x<getXSize();x++){
                if(array[y][x]){
                    gc.setFill(Color.LIGHTSLATEGREY);
                    gc.fillRect(x, y, 1, 1);
                }else{
                    gc.setFill(Color.WHITESMOKE);
                    gc.fillRect(x, y, 1, 1);
                }
            }
        }
        drawGrid(gc);
    }
    
    short getAliveNeighbour(short x, short y){       
        return (short) (
                getValueAt((short)(x-1),(short)(y-1)) + getValueAt((short)(x),(short)(y-1)) + getValueAt((short)(x+1),(short)(y-1)) + 
                getValueAt((short)(x-1),(short)(y))                                         +  getValueAt((short)(x+1),(short)(y)) +
                getValueAt((short)(x-1),(short)(y+1)) +  getValueAt((short)(x),(short)(y+1)) +  getValueAt((short)(x+1),(short)(y+1))
                
                );
    }
    
    
    void setRandomSpace(){
        for(short y=0;y<getYSize();y++){
            for(short x=0;x<getXSize();x++){
               array[y][x]=myRandom(0, 1);
            }
        }    
    }
    
    boolean myRandom(int min,int max){  
        return ThreadLocalRandom.current().nextInt(min, max + 1)==1;
    }
    
    void setRandomBiased(float f){//check      
        int max=100000;
        int min=0;
        int cut=(int) (max*f);
        for(short y=0;y<getYSize();y++){
            for(short x=0;x<getXSize();x++){
               array[y][x]=(ThreadLocalRandom.current().nextInt(min, max + 1)<cut);
            }
        }   
        
    }
    
    public final void drawGrid(GraphicsContext gc){        
            for(short j=0;j<getYSize();j++){                
                    gc.setStroke(Color.LIGHTSLATEGREY);
                    gc.setLineWidth(0.01);
                    gc.strokeLine(0, j, getXSize(), j);
            }
            
            for(short i=0;i<getXSize();i++){                
                    gc.setStroke(Color.LIGHTSLATEGREY);
                    gc.setLineWidth(0.01);
                    gc.strokeLine(i, 0,i,getYSize());
            }
        
    }
    
    
}
package com.escom.bmltraffic;

//package BML;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Space{
	private final byte[][] array;
	
	public Space(short x,short y){
		array = new byte[y][x];
		for(short j=0;j<y;j++){
            for(short i=0;i<x;i++){
				array[j][i]=0;
			}
		}
	}
	
	short getYSize(){
		return (short) array.length;
	}
    
    short getXSize(){
		return (short) array[0].length;
	}
	
    int getTotalSize(){
		return this.getXSize()*this.getYSize();
	}
    
    byte[][] getArray(){ 
		return array;
	}
	
	byte getValueAt(short x, short y){
        return (array[mod(y,this.getYSize())][mod(x,this.getXSize())]);
    }
    
	short mod(short a, short b){
        short r = (short) (a % b);
		return  (short) (r < 0 ? r + b : r);
    }
	
	void setValueAt(short x, short y,byte value){
        array[mod(y,this.getYSize())][mod(x,this.getXSize())] = value;
    }
	
	void drawSpace(GraphicsContext gc){        
        for(short y=0;y<getYSize();y++){
            for(short x=0;x<getXSize();x++){
				switch(array[y][x]){
					case 0:
						gc.setFill(Color.WHITE);
						gc.fillRect(x, y, 1, 1);
						break;
					case 1:
					    gc.setFill(Color.BLUE); // move downwards
						gc.fillRect(x, y, 1, 1);
						break;
					case 2:
					    gc.setFill(Color.RED);// move rightwards
						gc.fillRect(x, y, 1, 1);
						break;
					default:
						break;
				}

            }
        }
        drawGrid(gc);
    }
	
	boolean canMove(short x, short y, byte dir){  
		switch(dir){
                    case 1:
                        return getValueAt(x,(short)(y+1))==0;
                    case 2:
                        return getValueAt((short)(x+1),y)==0;
                    default:
                        return false;
		}
	}
	
    void setRandomBiased(float f){//check      
        int max=100000;
        int min=0;
        int cut=(int) (max*f);
        for(short y=0;y<getYSize();y++){
            for(short x=0;x<getXSize();x++){
               if(ThreadLocalRandom.current().nextInt(min, max + 1)<cut){
					array[y][x] = (byte)ThreadLocalRandom.current().nextInt(1,3);
			   }
            }
        } 
    }
	
	
	public boolean changeDirection(){
            return ThreadLocalRandom.current().nextInt(0,100)<10;
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
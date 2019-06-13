package com.escom.bmltraffic;

//package BML;

public class BMLAutomaton{
	Space getNextGeneration(Space currentEvolution){
		Space auxEvolution = new Space(currentEvolution.getXSize(), currentEvolution.getYSize());
		Space nextEvolution = new Space(currentEvolution.getXSize(), currentEvolution.getYSize());
                byte dir;
		
		for(short y=0;y<currentEvolution.getYSize();y++){
                    for(short x=0;x<currentEvolution.getXSize();x++){
				dir = currentEvolution.getValueAt(x,y);
				if(currentEvolution.changeDirection() && dir==1){
					currentEvolution.setValueAt(x,y,(byte)2);
					dir = 2;
				}
				if(dir==1){
					if(currentEvolution.canMove(x,y,dir)){
						auxEvolution.setValueAt(x,(short)(y+1),(byte)dir);
					}else{
						auxEvolution.setValueAt(x,y,(byte)dir);
					}
				}else{
					if(dir==2){
						auxEvolution.setValueAt(x,y,(byte)dir);
					}
				}
                    }
                }
		
		for(short y=0;y<currentEvolution.getYSize();y++){
			for(short x=0;x<currentEvolution.getXSize();x++){
				dir = auxEvolution.getValueAt(x,y);
				if(auxEvolution.changeDirection() && dir==2){
					auxEvolution.setValueAt(x,y,(byte)1);
					dir =1;
				}
				if(dir==2){
					if(auxEvolution.canMove(x,y,dir)){
						nextEvolution.setValueAt((short)(x+1),y,(byte)dir);
					}else{
						nextEvolution.setValueAt(x,y,(byte)dir);
					}
				}else{
					if(dir==1){
						nextEvolution.setValueAt(x,y,(byte)dir);
					}
				}
			}
		}
        return nextEvolution;
    }
}
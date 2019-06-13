package com.escom.gameoflife;

public class GameAutomaton {
    //B3 / S23
    //Birth / Survival
    //Life: 2-3   3-3
    short x1,y1,x2,y2;

    public GameAutomaton(short x1,short y1, short x2, short y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }
    
    Space getNextGeneration(Space currentEvolution){
        Space nextEvolution= new Space(currentEvolution.getXSize(), currentEvolution.getYSize());
        
        for(short x=0;x<currentEvolution.getXSize();x++){
            for(short y=0;y<currentEvolution.getYSize();y++){
                if(currentEvolution.getBooleanValueAt(x, y)){ //Alive Cell, Survive                    
                    if(currentEvolution.getAliveNeighbour(x, y)<x1||currentEvolution.getAliveNeighbour(x, y)>y1){ //Underpopulation
                        nextEvolution.setValueAt(x, y, false);
                    }else{
                        nextEvolution.setValueAt(x, y, true);
                    }                
                }else{ //Dead Cell, Birth                    
                    if(currentEvolution.getAliveNeighbour(x, y)<x2||currentEvolution.getAliveNeighbour(x, y)>y2){
                        nextEvolution.setValueAt(x, y, false);                    
                    }else{
                        nextEvolution.setValueAt(x, y, true);
                    }
                }
            
            }        
        }
        
        return nextEvolution;
    }
    
}

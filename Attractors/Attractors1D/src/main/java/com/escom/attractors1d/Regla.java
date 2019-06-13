/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escom.attractors1d;

public class Regla {
    boolean bits_regla[];
    int no_regla;
    
    boolean[] getBitsRegla(){return bits_regla;}
    
    public Regla(int no_regla){
        this.no_regla=no_regla;
        bits_regla=new boolean[8];
        
        for (int i = 7; i >= 0; i--) {
            bits_regla[i] = (no_regla & (1 << i)) != 0;         
        }
        
    }
    
    void aplicaRegla(Espacio actual, Espacio siguiente){
        for(int j=0;j<actual.getTamanio();j++){            
                            //Estado 1          1           1
                        if(actual.getArreglo()[mod((j-1),actual.getTamanio())]&&actual.getArreglo()[j]&&actual.getArreglo()[mod((j+1),actual.getTamanio())]){                
                            siguiente.setArregloAt(bits_regla[7],j);
                             //Estado 1               1           0
                        }else if(actual.getArreglo()[mod((j-1),actual.getTamanio())]&&actual.getArreglo()[j]&&!actual.getArreglo()[mod((j+1),actual.getTamanio())]){               
                            siguiente.setArregloAt(bits_regla[6],j);
                            //Estado 1                0            1
                        }else if(actual.getArreglo()[mod((j-1),actual.getTamanio())]&&!actual.getArreglo()[j]&&actual.getArreglo()[mod((j+1),actual.getTamanio())]){                
                            siguiente.setArregloAt(bits_regla[5],j);
                            //Estado    1             0            0
                        }else if(actual.getArreglo()[mod((j-1),actual.getTamanio())]&&!actual.getArreglo()[j]&&!actual.getArreglo()[mod((j+1),actual.getTamanio())]){                
                            siguiente.setArregloAt(bits_regla[4],j);
                            //Estado 0                 1           1
                        }else if(!actual.getArreglo()[mod((j-1),actual.getTamanio())]&&actual.getArreglo()[j]&&actual.getArreglo()[mod((j+1),actual.getTamanio())]){                
                            siguiente.setArregloAt(bits_regla[3],j);
                            //Estado 0                  1          0
                        }else if(!actual.getArreglo()[mod((j-1),actual.getTamanio())]&&actual.getArreglo()[j]&&!actual.getArreglo()[mod((j+1),actual.getTamanio())]){                
                            siguiente.setArregloAt(bits_regla[2],j);
                            //Estado   0                0          1
                        }else if(!actual.getArreglo()[mod((j-1),actual.getTamanio())]&&!actual.getArreglo()[j]&&actual.getArreglo()[mod((j+1),actual.getTamanio())]){                
                            siguiente.setArregloAt(bits_regla[1],j);
                            //Estado 0                 0            0
                        }else if(!actual.getArreglo()[mod((j-1),actual.getTamanio())]&&!actual.getArreglo()[j]&&!actual.getArreglo()[mod((j+1),actual.getTamanio())]){                
                            siguiente.setArregloAt(bits_regla[0],j);
                        }
        }
    
    }
    
    int mod(int a, int b){
        int r = a % b;
        return r < 0 ? r + b : r;
    }
    
}

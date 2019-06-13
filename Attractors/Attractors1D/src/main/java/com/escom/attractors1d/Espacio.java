
package com.escom.attractors1d;

import java.util.concurrent.ThreadLocalRandom;

public class Espacio {
    boolean arreglo [];
    static long no_combinaciones;
    
    public Espacio(int tamanio){       
        arreglo=new boolean[tamanio];
        no_combinaciones=(long) Math.pow(2,tamanio);    
    }
    
    int getTamanio(){
        return arreglo.length;
    }
    
    boolean [] getArreglo(){
        return arreglo;    
    }
    
    
    void setArregloAt(boolean b,int pos){
        arreglo[pos]=b;
    }
    
    void llenaArregloRandom(){     
        for(int i=0;i<arreglo.length;i++){
            arreglo[i]= myRandom(0, 1);
        }         
    }
    
    boolean myRandom(int min,int max){  
        return ThreadLocalRandom.current().nextInt(min, max + 1)==1;
    }
    
    
    void imprimeEspacio(){
        for(int i=arreglo.length-1;i>=0;i--){
            if(arreglo[i])
                System.out.print("1");
            else
                System.out.print("0");
        }        
        System.out.print("\n");    
    }
    
    void longToEspacio(long edo){
        for (int i = arreglo.length-1; i >= 0; i--) {
            arreglo[i] = (edo & (1 << i)) != 0;         
        }
        
    }
    
    long espacioToLong(){
        long n=0;
        
        for (int i = 0; i < arreglo.length; i++){
            if (arreglo[i])
                n += (Math.pow(2, i));
        }
        
        return n;    
    }
    
}

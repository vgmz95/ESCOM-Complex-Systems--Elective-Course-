/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escom.onedimensionalautomaton;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Victor
 */
public class Automaton {

    private static final int RULE_ARRAY_SIZE = 8;

    private int rule;
    private int evolutionNumber;
    private boolean[] evolutionarySpace;
    private final boolean[] auxArray;
    private final boolean[] ruleBits;

    public Automaton(int rule, int size) {
        this.rule = rule;
        this.evolutionNumber = 0;
        this.evolutionarySpace = new boolean[size];
        this.ruleBits = new boolean[RULE_ARRAY_SIZE];
        this.auxArray = new boolean[size];
        for (int i = ruleBits.length - 1; i >= 0; i--) {
            ruleBits[i] = (rule & (1 << i)) != 0;
        }
        
    }
    

    public void evolve() {
        int spaceSize = evolutionarySpace.length;
        for (int j = 0; j < spaceSize; j++) {
            //Estado 1          1           1
            if (evolutionarySpace[mod((j - 1), spaceSize)] && evolutionarySpace[j] && evolutionarySpace[mod((j + 1), spaceSize)]) {
                auxArray[j] = ruleBits[7];
                //Estado 1               1           0
            } else if (evolutionarySpace[mod((j - 1), spaceSize)] && evolutionarySpace[j] && !evolutionarySpace[mod((j + 1), spaceSize)]) {
                auxArray[j] = ruleBits[6];
                //Estado 1                0            1
            } else if (evolutionarySpace[mod((j - 1), spaceSize)] && !evolutionarySpace[j] && evolutionarySpace[mod((j + 1), spaceSize)]) {
                auxArray[j] = ruleBits[5];
                //Estado    1             0            0
            } else if (evolutionarySpace[mod((j - 1), spaceSize)] && !evolutionarySpace[j] && !evolutionarySpace[mod((j + 1), spaceSize)]) {
                auxArray[j] = ruleBits[4];
                //Estado 0                 1           1
            } else if (!evolutionarySpace[mod((j - 1), spaceSize)] && evolutionarySpace[j] && evolutionarySpace[mod((j + 1), spaceSize)]) {
                auxArray[j] = ruleBits[3];
                //Estado 0                  1          0
            } else if (!evolutionarySpace[mod((j - 1), spaceSize)] && evolutionarySpace[j] && !evolutionarySpace[mod((j + 1), spaceSize)]) {
                auxArray[j] = ruleBits[2];
                //Estado   0                0          1
            } else if (!evolutionarySpace[mod((j - 1), spaceSize)] && !evolutionarySpace[j] && evolutionarySpace[mod((j + 1), spaceSize)]) {
                auxArray[j] = ruleBits[1];
                //Estado 0                 0            0
            } else if (!evolutionarySpace[mod((j - 1), spaceSize)] && !evolutionarySpace[j] && !evolutionarySpace[mod((j + 1), spaceSize)]) {
                auxArray[j] = ruleBits[0];
            }
        }
        evolutionarySpace = Arrays.copyOf(auxArray, auxArray.length);      
        evolutionNumber++;
    }

    public void randomize() {
        for (int i = 0; i < evolutionarySpace.length; i++) {
            evolutionarySpace[i] = myRandom(0, 1);
        }
        evolutionNumber = 0;
    }

    boolean myRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1) == 1;
    }

    int mod(int a, int b) {
        int r = a % b;
        return r < 0 ? r + b : r;
    }

    public int getEvolutionNumber() {
        return evolutionNumber;
    }

    public int getRegla() {
        return rule;
    }

    public void setRegla(int regla) {
        this.rule = regla;
    }

    public boolean[] getArreglo() {
        return evolutionarySpace;
    }

    public void setArreglo(boolean[] arreglo) {
        this.evolutionarySpace = arreglo;
    }
    
    public int getSpaceLenght(){
        return evolutionarySpace.length;
    }

}

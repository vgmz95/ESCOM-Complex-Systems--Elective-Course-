/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;

/**
 *
 * @author Victor
 */
public class AtractoresLife {
    
    public static void main(String[] args) throws IOException{
        System.out.println("Game of Life.\nInsert rule (x1,y1,x2,y2), leaving a space after a number: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String rule_not=br.readLine();
        String[] split = rule_not.split(" ");
        short x1=Short.parseShort(split[0]),y1=Short.parseShort(split[1]),x2=Short.parseShort(split[2]),y2=Short.parseShort(split[3]);
        GameAutomaton game=new GameAutomaton(x1, y1, x2, y2);
        calculaAtractores(game,(short)2);
        calculaAtractores(game,(short)3);
        calculaAtractoresFuerzaBruta(game, (short)4);
    }

    private static void calculaAtractores(GameAutomaton game, short size) throws IOException {
        
        Space currentEvol=new Space(size, size);
        Space nextEvol;
        short corresp_evol;
        short possibleComb=(short) Math.pow(2, size*size);
        System.out.println("Hay "+possibleComb+" posibles combinaciones.");
        DirectedGraph<Short, DefaultEdge> Grafo = new DefaultDirectedGraph<> (DefaultEdge.class);
        
        for(short i=0;i<possibleComb;i++){//
            currentEvol.inttoSpace(i);
            nextEvol=game.getNextGeneration(currentEvol);
            corresp_evol=(short) nextEvol.spacetoInt();
            Grafo.addVertex(i);
            Grafo.addVertex(corresp_evol);
            Grafo.addEdge(i,corresp_evol);
        }
        
        ConnectivityInspector insec= new ConnectivityInspector(Grafo);
        List connectedSets = insec.connectedSets();
        System.out.println("Hay en total "+connectedSets.size()+" subconjuntos");
        
        int n=0;
        //Isomorfismo grafos
        FileOutputStream isomor=new FileOutputStream(game.x2+"_"+game.y2+"_"+game.x1+"_"+game.y1+"_"+size+".txt");
        String report_iso;
        report_iso="Equivalencias de nodos: \n";
        isomor.write(report_iso.getBytes());
        
        int nom=1;
        boolean rep=true;
        
        for(;;){
            for(int i=0;i<connectedSets.size();++i){            
                DirectedSubgraph Subgrafo1=new DirectedSubgraph(Grafo, (Set) connectedSets.get(i),Grafo.edgeSet());
                for(int j=i+1;j<connectedSets.size();++j){
                    DirectedSubgraph Subgrafo2= new DirectedSubgraph(Grafo, (Set) connectedSets.get(j),Grafo.edgeSet());
                    VF2GraphIsomorphismInspector hayIsomorfismo= new VF2GraphIsomorphismInspector(Subgrafo1, Subgrafo2);
                    if(hayIsomorfismo.isomorphismExists()){
                        //Archivo
                        
                            report_iso="Equivalencia "+nom+"\n ";
                            isomor.write(report_iso.getBytes());
                            isomor.write((Subgrafo1.toString()+" \n ").getBytes());
                            isomor.write((Subgrafo2.toString()+" \n ").getBytes());
                        
                        connectedSets.remove(j);
                        ++n;
                        nom++;
                    }else{
                        //System.out.print("No tiene isomorfismos");

                    }
               }
            }
            
            if(n>0)
                n=0;
            else
                break;
        
        }
        
        if(nom==1){
             report_iso="Ninguna";
             isomor.write(report_iso.getBytes());        
        }
            
        
        isomor.close();
        
        
        //Writing to file////////
            FileOutputStream archivo= new FileOutputStream(game.x2+"_"+game.y2+"_"+game.x1+"_"+game.y1+"_"+size+".nb");
            final String cadena_inicial="Graph[{";
            final String cadena_final="}]";
            String cadenas_par;
            //Graph[{1 -> 2, 2 -> 3, 3 ->1}]
            archivo.write(cadena_inicial.getBytes());
            for(int i=0;i<connectedSets.size();i++){
            Set<Short> s = (Set<Short>) connectedSets.get(i);
            Iterator<Short> iterator = s.iterator();
                while(iterator.hasNext()){
                    int next = iterator.next();
                    currentEvol.inttoSpace(next);
                    nextEvol=game.getNextGeneration(currentEvol);
                    corresp_evol=(short) nextEvol.spacetoInt();                    
                    cadenas_par=next+"->"+corresp_evol;
                    if(i==connectedSets.size()-1&&!iterator.hasNext()){
                       
                    }else{
                     cadenas_par+=",";
                    }

                archivo.write(cadenas_par.getBytes());

                }
            }
        archivo.write(cadena_final.getBytes());
        archivo.flush();
        archivo.close();  
        
        
    }
    
    private static void calculaAtractoresFuerzaBruta(GameAutomaton game, short size ) throws FileNotFoundException, IOException{
        Space currentEvol=new Space(size, size);
        Space nextEvol;
        int corresp_evol;
        int possibleComb=(int) Math.pow(2, size*size);
        System.out.println("Hay "+possibleComb+" posibles combinaciones.");
        //File
            FileOutputStream archivo= new FileOutputStream(game.x2+"_"+game.y2+"_"+game.x1+"_"+game.y1+"_"+size+".nb");
            final String cadena_inicial="Graph[{";
            final String cadena_final="}]";
            String cadenas_par;
            archivo.write(cadena_inicial.getBytes());
            
        for(int i=0;i<possibleComb;i++){
            currentEvol.inttoSpace(i);
            nextEvol=game.getNextGeneration(currentEvol);
            corresp_evol= nextEvol.spacetoInt();
            cadenas_par=i+"->"+corresp_evol;
            if(i!=possibleComb-1){
                cadenas_par+=",";
            }
            
            archivo.write(cadenas_par.getBytes());
        }
        
        archivo.write(cadena_final.getBytes());
        archivo.flush();
        archivo.close(); 
        
    }
    
}

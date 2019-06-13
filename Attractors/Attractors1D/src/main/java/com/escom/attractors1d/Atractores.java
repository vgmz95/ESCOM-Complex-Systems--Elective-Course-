package com.escom.attractors1d;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;


public class Atractores {
   
    
    public static void main(String[] args) throws IOException {
        System.out.println("Ingrese la regla: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int no_regla;
        no_regla=Integer.parseInt(br.readLine());
        Regla regla=new Regla(no_regla);
        
        System.out.println("Ingrese el espacio de evoluciones: ");
        int tam_espacio=Integer.parseInt(br.readLine());        
        Espacio espacio_actual = new Espacio(tam_espacio);
        Espacio espacio_siguiente= new Espacio(tam_espacio);               
        System.out.println("Número de posibles combinaciones: "+Espacio.no_combinaciones);  
        
        obtieneAtractores(regla,espacio_actual,espacio_siguiente);        
    }   

    static void obtieneAtractores(Regla regla, Espacio espacio_actual, Espacio espacio_siguiente) throws IOException {
        long id_correspondiente=0;        
        DirectedGraph<Long, DefaultEdge> Grafo = new DefaultDirectedGraph<> (DefaultEdge.class);
          
        for(long i=0;i<Espacio.no_combinaciones;++i){
            espacio_actual.longToEspacio(i);
            regla.aplicaRegla(espacio_actual, espacio_siguiente);
            id_correspondiente=espacio_siguiente.espacioToLong();
            //Grafo
            Grafo.addVertex(i);
            Grafo.addVertex(id_correspondiente);
            Grafo.addEdge(i,id_correspondiente);           
        }
        
        
        ConnectivityInspector insec= new ConnectivityInspector(Grafo);
        List connectedSets = insec.connectedSets();
        System.out.println("Hay en total "+connectedSets.size()+" subconjuntos");
       
        int n=0;
        //Isomorfismo grafos
        FileOutputStream isomor=new FileOutputStream(regla.no_regla+"_"+espacio_actual.getTamanio()+".txt");
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
                                              
                        System.out.println("Los siguientes grafos son equivalentes: ");
                        System.out.println(Subgrafo1);
                        System.out.println(Subgrafo2);
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
        
        FileOutputStream archivo;
        try {
            archivo= new FileOutputStream(regla.no_regla+"_"+espacio_actual.getTamanio()+".nb");
            final String cadena_inicial="Graph[{";
            final String cadena_final="}]";
            String cadenas_par;
            //Graph[{1 -> 2, 2 -> 3, 3 ->1}]
            archivo.write(cadena_inicial.getBytes());
            for(int i=0;i<connectedSets.size();i++){
            Set<Long> s = (Set<Long>) connectedSets.get(i);
            Iterator<Long> iterator = s.iterator();
                while(iterator.hasNext()){
                    long next = iterator.next();
                    espacio_actual.longToEspacio(next);
                    regla.aplicaRegla(espacio_actual, espacio_siguiente);
                    id_correspondiente=espacio_siguiente.espacioToLong();
                    cadenas_par=next+"->"+id_correspondiente;
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Atractores.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        
        System.out.println();
        
        
    }
    
    
    

   
    
}




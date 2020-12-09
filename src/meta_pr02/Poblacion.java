/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_pr02;

import java.util.ArrayList;
import java.util.HashSet;
import javafx.util.Pair;

/**
 *
 * @author spdlc
 */
public class Poblacion {
    
    private ArrayList<Individuo> v_poblacion;

    
    public Poblacion (){
        v_poblacion = new ArrayList<>();
    }
    
    public Poblacion(Random _random, Integer num_individuos,ArchivoDatos _archivo){
        /*INICIALIZACIÓN DE LA POBLACIÓN*/
        v_poblacion = new ArrayList<>();

        while (v_poblacion.size() < num_individuos){
            Individuo ind = new Individuo(_archivo,_random);
            v_poblacion.add(ind);
        }
    }

    public void mostrarPoblacion(){
        getV_poblacion().forEach((individuo) -> {
            System.out.println(individuo.getCromosoma());
            System.out.println(individuo.getCromosoma().size()+" genes del cromosoma --> "+individuo.getFitness()+" de coste fitness.");
        });
    }
    
    public ArrayList<Pair<Individuo,Double>> ordenSegunFitness(){
        Pair<Individuo,Double> individuo_fitness;
        ArrayList<Pair<Individuo,Double>> IndividuosFitness = new ArrayList();
        
        for (int i = 0; i < v_poblacion.size(); i++){
            individuo_fitness = new Pair<>(v_poblacion.get(i),v_poblacion.get(i).getFitness());
            IndividuosFitness.add(individuo_fitness);
        }
       IndividuosFitness.sort((o1,o2) -> o1.getValue().compareTo(o2.getValue()));
       return IndividuosFitness;
    }

    /**
     * @return the v_poblacion
     */
    public ArrayList<Individuo> getV_poblacion() {
        return v_poblacion;
    }

    /**
     * @param v_poblacion the v_poblacion to set
     */
    public void setV_poblacion(HashSet<Individuo> v_poblacion) {
        v_poblacion.forEach((ind) -> {
            this.v_poblacion.add(ind);
        });
    }
    
}

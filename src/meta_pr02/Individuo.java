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
public final class Individuo {
    
    private final float[][] matrizDatos; //Matriz de datos con la que trabajará el individuo.
    
    
    private final int num_elementos; //Tamaño completo de elementos que pueden ser o no parte de la Solución.
    private final int num_candidatos; //Tamaño que debe de ocupar el conjunto de elementos de la solución.
    private HashSet<Integer> cromosoma; //Contenedor de la solución de 1 individuo.
    private HashSet<Integer> N; //Contenedor de los elementos no pertenecientes al cromosoma.
    private double fitness; //Coste asociado al cromosoma.
    
    public Individuo(ArchivoDatos _archivo,ArrayList<Integer> _cromosoma){
        matrizDatos = _archivo.getMatrizDatos();
        num_elementos = _archivo.getTamMatriz();
        num_candidatos = _archivo.getTamSolucion();
        
        /*INICIALIZACION DE TODOS LOS ELEMENTOS*/
        N = new HashSet<>(num_elementos);
        for (int i=0; i < num_elementos; i++)
            N.add(i);

        cromosoma = new HashSet<>();
        for (int i = 0; i < _cromosoma.size(); i++){
            cromosoma.add(_cromosoma.get(i));
            N.remove(_cromosoma.get(i));
        }
        /*COSTE INICIAL DEL INDIVIDUO*/
        fitness = costeFitness(cromosoma);
    }
    
    public Individuo(Long sem,ArchivoDatos _archivo, Random random){
        
        matrizDatos = _archivo.getMatrizDatos();
        num_elementos = _archivo.getTamMatriz();
        num_candidatos = _archivo.getTamSolucion();
        
        /*INICIALIZACION DE TODOS LOS ELEMENTOS*/
        N = new HashSet<>(num_elementos);
        for (int i=0; i < num_elementos; i++)
            N.add(i);
        
        /*INICIALIZACIÓN DEL CROMOSOMA.*/
        int punto;
        
        cromosoma = new HashSet<>(num_candidatos);
        while (cromosoma.size() < num_candidatos){
            punto = random.Randint(0, num_elementos-1);
            cromosoma.add(punto);
            N.remove(punto);
        }
        
        /*COSTE INICIAL DEL INDIVIDUO*/
        fitness = costeFitness(cromosoma);
         
    }
    
    /**
     * 
     * @brief Sumatoria del coste final.
     * @post La suma de todas las distancias de cada uno de los puntos con respecto a los demás puntos.
     * @param cromosoma Conjunto de elementos que representa a la solución.
     * @return Sumatoria final.
     */
    public double costeFitness(HashSet<Integer> cromosoma){
        double coste = 0.0;
        ArrayList<Integer> v_M = new ArrayList<>(cromosoma);
        for(int i = 0; i < v_M.size()- 1; i++)
            for(int j = i+1; j < v_M.size(); j++){
                if(matrizDatos[v_M.get(i)][v_M.get(j)] != 0)
                    coste += matrizDatos[v_M.get(i)][v_M.get(j)];
                else
                    coste += matrizDatos[v_M.get(j)][v_M.get(i)];
            }
        return coste;
    }
    
    
    /**
     * 
     * @brief Distancias de un elemento respecto a los candidatos.
     * @post Método general para cualquier algoritmo que usemos, ya que se deberá comprobar el coste
     * de un punto respecto a todos los demás de la solución.
     * @param elem Entero correspondiente que se quiere comprobar su distancia con los demás candidatos.
     * @return Suma de las distancias del elemento del parámetro con todos los candidatos.
     */
    protected double distanciasElemento(Integer elem){
        double sumaDistancias = 0;
        for(Integer i : cromosoma)
            if(matrizDatos[i][elem] != 0)
                sumaDistancias += matrizDatos[i][elem];
            else
                sumaDistancias += matrizDatos[elem][i];
        return sumaDistancias;
    }
    
    
    /**
     * @brief Método de intercambio de elementos.
     * @post Se realiza el intercambio entre elementos indicados como parámetros
     * para el HashSet de la solución, el ArrayList que simula a la solución Hashset.
     * A la misma vez, se hace el intercambio inverso al HashSet de los elementos no seleccionados
     * en la solución y en su ArrayList correspondiente.
     * @param seleccionado
     * @param j
     * @param v_n
     * @param v_M 
     */
    private void intercambiar(Integer seleccionado, Integer j,ArrayList<Integer> v_n, ArrayList<Integer> v_M){
        v_M.remove(seleccionado);
        v_M.add(j);
        cromosoma.remove(seleccionado);
        cromosoma.add(j);
        v_n.remove(j);
        v_n.add(seleccionado);
        N.remove(j);
        N.add(seleccionado);
    }
    
    /**
     * @brief Ordenación respecto Aporte/Elemento.
     * @post Ordena el vector de aportes que representa cada elemento de la solución con respecto a los demás.
     * @param v_distancias Contenedor Pair que indica el elemento y su aporte correspondiente.
     * @param solucion Conjunto de elementos que representa a la solución.
     */
    protected void ordenacionMenorAporte(ArrayList<Pair<Integer,Double>> v_distancias, HashSet<Integer> solucion){
        v_distancias.clear();
        ArrayList<Integer> v_solucion = new ArrayList<>(solucion);
        Pair<Integer,Double> añadir;
        for (int i = 0; i < v_solucion.size(); i++){
            añadir = new Pair<>(v_solucion.get(i),distanciasElemento(v_solucion.get(i)));
            v_distancias.add(añadir);
        }
       v_distancias.sort((o1,o2) -> o1.getValue().compareTo(o2.getValue()));
    }
    
    /**
     * @brief Método de factorización.
     * @post Método de factorización que intercambia el valor entre 2 elementos seleccionados; es decir, una factorización 2-opt.
     * @param seleccionado Elemento que se quiere eliminar de la solución.
     * @param j Elemento que se quiere añadir en la solución.
     * @param solucion Contiene el conjunto de elementos que forman una solución.
     * @param coste_actual Coste de la solución.
     * @param matrizDatos 
     * @return Double que indica el nuevo valor de la solución una vez hecha la factorización con "seleccionado" y "j".
     */
    protected double factorizacion(int seleccionado,int j,HashSet<Integer> solucion, double coste_actual, float[][] matrizDatos){
        ArrayList<Integer> v_M = new ArrayList<>(solucion);
        double costeMenor = 0, costeMayor =0;
        for (int k=0; k < v_M.size(); k++){
            if (v_M.get(k)!= seleccionado){
                if (matrizDatos[seleccionado][v_M.get(k)] != 0)
                    costeMenor += matrizDatos[seleccionado][v_M.get(k)];
                else
                    costeMenor += matrizDatos[v_M.get(k)][seleccionado];
            }
            if (v_M.get(k)!= seleccionado){
                if(matrizDatos[j][v_M.get(k)] != 0)
                    costeMayor+= matrizDatos[j][v_M.get(k)];
                else
                    costeMayor+= matrizDatos[v_M.get(k)][j];
            }
        }

        return coste_actual + costeMayor-costeMenor;

    }
    
    /**
     * @return the num_elementos
     */
    public int getNum_elementos() {
        return num_elementos;
    }

    /**
     * @return the num_candidatos
     */
    public int getNum_candidatos() {
        return num_candidatos;
    }
    
    /**
     * @return the cromosoma
     */
    public HashSet<Integer> getCromosoma() {
        return cromosoma;
    }

    /**
     * @return the n
     */
    public HashSet<Integer> getN() {
        return N;
    }
    
    /**
     * @return the coste
     */
    public double getFitness() {
        return fitness;
    }
}

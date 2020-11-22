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
public class AlgGenetico_Generacional {
    /*ATRIBUTOS PARA LA CARGA DE FICHEROS*/
    private final Configurador config;
    private final ArchivoDatos archivo;
    private final Random random;
    
    
    private final int NUM_ELEMENTOS; //Tamaño completo de elementos que pueden ser o no parte de la Solución.
    private final int NUM_CANDIDATOS; //Tamaño que debe de ocupar el conjunto de elementos de la solución.
    private Poblacion poblacion;
    private int evaluaciones;
    
    public AlgGenetico_Generacional(String[] _args,Integer num_archivo, Integer sem){
        config = new Configurador(_args[0]);
        archivo = new ArchivoDatos(config.getArchivos().get(num_archivo));
        random = new Random(config.getSemillas().get(sem));

        NUM_ELEMENTOS = archivo.getTamMatriz();
        NUM_CANDIDATOS = archivo.getTamSolucion();
        evaluaciones = 0;
        
        poblacion = new Poblacion(random,NUM_CANDIDATOS, archivo);//TODO: CREAR PARAMETRO CONFIGURABLE DEL NÚMERO DE INDIVIDUOS DE LA
    }
    
    
    public void ejecucionAGG(){
        
        /*ESTRUCTURAS NECESARIAS A LO LARGO DEL PROCESO DEL ALGORITMO GENÉTICO GENERACIONAL*/
        Poblacion nuevaPoblacion = new Poblacion(); //POBLACIÓN CON LA QUE REALIZAMOS EL PROCESO EVOLUTIVO.
        
        /*PRUEBAS COMO SI FUESE UNA UNICA VEZ EL PROCESO.*/
        torneoBinario(nuevaPoblacion); //FUNCIONA CORRECTAMENTE. COMPROBADO.
        
        
        nuevaPoblacion = cruce(nuevaPoblacion);
        System.out.println(evaluaciones);
        while (evaluaciones < config.getMAX_ITERACIONES()){
//            torneoBinario(nuevaPoblacion); //FUNCIONA CORRECTAMENTE. COMPROBADO.
//            nuevaPoblacion = cruce(nuevaPoblacion);
            evaluaciones++;
        }
    }
        
    /*---- MÉTODOS PRIVADOS NECESARIOS PARA LA EJECUCIÓN DEL ALGORITMO ----*/
    
    /*---- MÉTODO DE SELECCIÓN ----*/
    /**
     * @brief TORNEO BINARIO.
     * @post Método de selección de la población padre para generar una nueva población
     * seleccionando el mejor de 2 individuos elegidos al azar hasta completar el vector
     * de individuos que debe tener una población.
     * @param p Población nueva a generarse.
     */
    private void torneoBinario (Poblacion p){
        int pos1,pos2;
        while (p.getV_poblacion().size() < NUM_CANDIDATOS){
            do{
                pos1 = random.Randint(0, NUM_CANDIDATOS-1);
                pos2 = random.Randint(0, NUM_CANDIDATOS-1); //SEGUNDO INDIVIDUO A ENFRENTAR QUE NO SEA EL MISMO QUE EL PRIMERO.
            }while (pos1 != pos2);
            
            if(poblacion.getV_poblacion().get(pos1).getFitness() >= poblacion.getV_poblacion().get(pos2).getFitness())
                p.getV_poblacion().add(poblacion.getV_poblacion().get(pos1));
            else
                p.getV_poblacion().add(poblacion.getV_poblacion().get(pos2));
        }
    }
    
    /*---- MÉTODOS DE CRUCE ----*/
    
    private Poblacion cruce(Poblacion origen){
        
        /*Pasamos como parametro la población previa a crucarse, por lo que creamos una población destino*/
        Poblacion destinatario = new Poblacion();
        if ("2puntos".equals(config.getTIPO_CRUCE())){
            cruce2puntos(origen,destinatario);
        }else{
            cruceMPX(origen,destinatario);
        }
        return destinatario; //Población cruzada y reparada (en caso de tener que hacerse).

    }
    
    private void cruce2puntos(Poblacion origen, Poblacion destinatario){
        for(int i = 0; i < origen.getV_poblacion().size(); i += 2){ //COMPRUEBO QUE ITERA O LA MITAD DE VECES DE UNA SOLUCIÓN O QUE ESTÉ COMPLETO EL DESTINATARIO
            if (random.Randfloat(0,1) < config.getPROB_CRUCE()){
                HashSet<Integer> hijo1, hijo2;
                hijo1 = new HashSet<>();
                hijo2 = new HashSet<>();
                int punto1, punto2;
                do {
                    punto1 = random.Randint(0, NUM_CANDIDATOS-1);
                    punto2 = random.Randint(0, NUM_CANDIDATOS-1);
                }while (punto1 == punto2); //Para evitar no cruzar, comprobamos que no sean los mismos puntos.
                
                if(punto1 > punto2){ //Por si el punto1 es mayor que le segundo punto2. Hacer intercambio.
                    int aux = punto1; punto1 = punto2; punto2 = aux;
                }
                /*Añadimos en los individuos hijos los padres cruzados*/
                ArrayList<Integer> padre1 = new ArrayList<>(origen.getV_poblacion().get(i).getCromosoma());
                ArrayList<Integer> padre2 = new ArrayList<>(origen.getV_poblacion().get(i+1).getCromosoma());
                for(int j = 0; j < padre1.size() ; j++){ //Recorro los cromosomas de los individuos padres
                    if(j < punto1 || j > punto2){ //ANTES DE CRUZAR, SE COPIA EN EL HIJO LOS GENES SIN CRUZAR DEL CROMOSOMA PADRE.
                        hijo1.add(padre1.get(j));
                        hijo2.add(padre2.get(j));
                    }
                    else if (j >= punto1 && j <= punto2){
                        hijo1.add(padre2.get(j));
                        hijo2.add(padre1.get(j));
                    } 
                }
                
                /*COMPROBAMOS QUE NO HAYA QUE REPARAR A LOS HIJOS GENERADOS.*/
                
                Individuo individuoHijo1 = new Individuo(archivo, new ArrayList<>(hijo1));
                if (hijo1.size() < archivo.getTamSolucion())
                    repara2puntos(individuoHijo1);

                
                Individuo individuoHijo2 = new Individuo(archivo, new ArrayList<>(hijo2));
                if (hijo2.size() < archivo.getTamSolucion())
                    repara2puntos(individuoHijo2);
                
                /*UNA VEZ TENEMOS LOS 2 INDIVIDUOS HIJOS CRUZADOS (Y REPARADOS EN CASO DE TENER QUE HACERLO) ENTONCES SE AÑADEN A LA POBLACIÓN*/
                individuoHijo1.costeFitness();
                evaluaciones++;
                destinatario.getV_poblacion().add(individuoHijo1);
                individuoHijo2.costeFitness();
                evaluaciones++;
                destinatario.getV_poblacion().add(individuoHijo2);
                
            }
            /*En caos de no tener que cruzarse por probabilidad, se copian los padres al destinatario*/
            else{
                destinatario.getV_poblacion().add(origen.getV_poblacion().get(i));
                destinatario.getV_poblacion().add(origen.getV_poblacion().get(i+1));
            }
        }
//        for (int i = 0; i < config.getNUM_INDIVIDUOS(); i++)
//            System.out.println(destinatario.getV_poblacion().get(i).getCromosoma().size());
//        System.out.println(destinatario.getV_poblacion().size());
    }
    
    private void repara2puntos(Individuo ind){
        while(ind.getCromosoma().size() < archivo.getTamSolucion()){
            double distMax = 0;
            int elem_seleccionado = -1;
            for (Integer j : ind.getN()){
                double aux = ind.distanciasElemento(j);
                if(distMax < aux){
                    distMax = aux;
                    elem_seleccionado = j;
                }
            }
            ind.getCromosoma().add(elem_seleccionado);
            ind.getN().remove(elem_seleccionado);
        }
    }
    
    private void cruceMPX(Poblacion origen, Poblacion destinatario){
        /*Se obtiene 1 hijo a partir de 2 padres*/
        for(int i = 0; i < origen.getV_poblacion().size(); i += 2){
            if (random.Randfloat(0,1) < config.getPROB_CRUCE()){
                /*HAY QUE CRUZAR ENTONCES HAGO EL CRUCE 2 VECES PORQUE ES NECESARIO 2 RESULTADOS DE CRUCE*/
                HashSet<Integer> hijo1 = new HashSet<>();
                HashSet<Integer> hijo2 = new HashSet<>();
                hijoMPX(origen.getV_poblacion().get(i),origen.getV_poblacion().get(i+1),hijo1);
                hijoMPX(origen.getV_poblacion().get(i+1),origen.getV_poblacion().get(i),hijo2);
                
                
                Individuo ind1 = new Individuo(archivo, new ArrayList<>(hijo1));
                if (hijo1.size() > archivo.getTamSolucion())
                    reparaMPX(ind1);
                
                
                Individuo ind2 = new Individuo(archivo, new ArrayList<>(hijo2));
                if (hijo2.size() > archivo.getTamSolucion())
                    reparaMPX(ind2);

                
                /*UNA VEZ TENEMOS LOS 2 INDIVIDUOS HIJOS CRUZADOS (Y REPARADOS EN CASO DE TENER QUE HACERLO) ENTONCES SE AÑADEN A LA POBLACIÓN*/
                ind1.costeFitness();
                System.out.println(ind1.getCromosoma().size()+" --> "+ind1.getFitness());
                evaluaciones++;
                destinatario.getV_poblacion().add(ind1);
                ind2.costeFitness();
                System.out.println(ind2.getCromosoma().size()+" --> "+ind2.getFitness());
                evaluaciones++;
                destinatario.getV_poblacion().add(ind2);

            }
            else{
                destinatario.getV_poblacion().add(origen.getV_poblacion().get(i));
                destinatario.getV_poblacion().add(origen.getV_poblacion().get(i+1));
            }
        }
    }
    
    private void hijoMPX(Individuo i, Individuo j, HashSet<Integer> hijo){
        ArrayList<Integer> padre1 = new ArrayList<>(i.getCromosoma());
        ArrayList<Integer> padre2 = new ArrayList<>(j.getCromosoma());
        for(int k = 0; k < padre2.size(); k++)
            hijo.add(padre2.get(k));
        int numPadre1 = (int)(padre1.size()*config.getPORCENTAJE_MPX());
        int p1 = (int)(padre1.size()/2) - 1;
        int p2 = (int)(padre1.size()/2);
        while(numPadre1 > 0){
            hijo.add(padre1.get(p1));
            hijo.add(padre1.get(p2));
            --p1; ++p2;
            numPadre1 -= 2;
        }  
    }
    
    private void reparaMPX (Individuo ind){
        ArrayList<Pair<Integer,Double>> v_aportes = new ArrayList<>();
        ind.ordenacionMenorAporte(v_aportes);
        int num_borrar = ind.getCromosoma().size() - archivo.getTamSolucion();
        for(int i = 0; i < num_borrar; i++) /*Bucle de eliminar todos los sobrantes de menor aporte*/
            ind.getCromosoma().remove(v_aportes.get(i).getKey());
    }
    
    /*---- MÉTODO DE MUTACIÓN ----*/
    
    
    /*---- MÉTODO DE EVALUACIÓN ----*/
    
    
    /*---- MÉTODO DE REEMPLAZO ----*/
    
   
}

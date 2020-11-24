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
    
    private Individuo MEJOR_INDIVIDUO;
    private Double MEJOR_FITNESS;
    
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
        
        poblacion = new Poblacion(random,config.getNUM_INDIVIDUOS(), archivo);
        ArrayList<Integer> individuo_mejor = new ArrayList<>(archivo.getTamSolucion());
        MEJOR_INDIVIDUO = new Individuo(archivo, individuo_mejor);
        MEJOR_INDIVIDUO.costeFitness();
        MEJOR_FITNESS = MEJOR_INDIVIDUO.getFitness();
    }
    
    
    public void ejecucionAGG(){ 
        evaluacion(poblacion);
        while (evaluaciones < config.getMAX_ITERACIONES()) {
            Poblacion nuevaPoblacion = torneoBinario(); 
            Poblacion poblacion_cruzada = cruce(nuevaPoblacion);
            Poblacion poblacion_mutada = mutacion(poblacion_cruzada);
            evaluacion(poblacion_cruzada);
            poblacion = reemplazo(config.getNUM_ELITE_INDIVIDUOS(), poblacion_mutada);
            System.out.println("MEJOR FITNESS ENCONTRADO --> " + MEJOR_FITNESS + " :: Nº EVALUACIONES = " + evaluaciones);
        }
    }
        
    
    /*---- MÉTODOS PRIVADOS NECESARIOS PARA LA EJECUCIÓN DEL ALGORITMO ----*/
    
    /*---- MÉTODO DE SELECCIÓN ----*/
    /**
     * @brief TORNEO BINARIO.
     * @post Método de selección de la población padre para generar una nueva población
     * seleccionando el mejor de 2 individuos elegidos uno por iteración para dar la 
     * oportunidad a todos los individuos y el otro al azar hasta completar el vector
     * de individuos que debe tener una población.
     * @param p Población nueva a generarse.
     */
    private Poblacion torneoBinario (){
        Poblacion p = new Poblacion();
        int pos,i;
        while (p.getV_poblacion().size() < config.getNUM_INDIVIDUOS()){
            do{ 
                i = random.Randint(0, config.getNUM_INDIVIDUOS()-1);
                pos = random.Randint(0, config.getNUM_INDIVIDUOS()-1); //SEGUNDO INDIVIDUO A ENFRENTAR QUE NO SEA EL MISMO QUE EL PRIMERO.
            }while (i != pos);
            
            if(poblacion.getV_poblacion().get(i).getFitness() >= poblacion.getV_poblacion().get(pos).getFitness())
                p.getV_poblacion().add(poblacion.getV_poblacion().get(i));
            else
                p.getV_poblacion().add(poblacion.getV_poblacion().get(pos));
        }
        return p;
    }
    
    /*---- MÉTODOS DE CRUCE ----*/
    
    private Poblacion cruce(Poblacion origen){
        /*Pasamos como parametro la población previa a cruzarse, por lo que creamos una población destino*/
        Poblacion destinatario = new Poblacion();
        if ("2puntos".equals(config.getTIPO_CRUCE())){
            cruce2puntos(origen,destinatario);
        }else{
            cruceMPX(origen,destinatario);
        }
        return destinatario; 

    }
    
       
    //TODO: DEVOLVER UN LIST DE LOS HIJOS Y COMO PARAMETROS LOS PADRES A CRUZAR. PARA EVITAR DUPLICAR CODIGO
    private void cruce2puntos(Poblacion origen, Poblacion destinatario){
        
        for(int i = 0; i < origen.getV_poblacion().size(); i += 2){ //COMPRUEBO QUE ITERA O LA MITAD DE VECES DE UNA SOLUCIÓN O QUE ESTÉ COMPLETO EL DESTINATARIO
            
            if (random.Randfloat(0,1) < config.getPROB_CRUCE()){
                
                /*--- GENERAR 2 PUNTOS PARA CRUZAR ----*/
                int punto1, punto2;
                do {
                    punto1 = random.Randint(1, NUM_CANDIDATOS-2);
                    punto2 = random.Randint(1, NUM_CANDIDATOS-2);
                }while (punto1 == punto2); //Para evitar no cruzar, comprobamos que no sean los mismos puntos.
                
                if(punto1 > punto2){ //Por si el punto1 es mayor que le segundo punto2. Hacer intercambio.
                    int aux = punto1; punto1 = punto2; punto2 = aux;
                }
                /*-------------------------------------*/
                
                
                /*--- REALIZAR EL CRUCE EN LOS CROMOSOMAS HIJOS ---*/
                ArrayList<Integer> padre1 = new ArrayList<>(origen.getV_poblacion().get(i).getCromosoma()); /*Añadimos en los individuos hijos los padres cruzados*/
                ArrayList<Integer> padre2 = new ArrayList<>(origen.getV_poblacion().get(i+1).getCromosoma());/*Añadimos en los individuos hijos los padres cruzados*/
                HashSet<Integer> hijo1 = new HashSet<>();
                HashSet<Integer> hijo2 = new HashSet<>();
                
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
                /*-------------------------------------------------*/
                
                /*--- COMPROBAMOS QUE NO HAYA QUE REPARAR A LOS HIJOS GENERADOS ---*/
                Individuo individuoHijo1 = new Individuo(archivo, new ArrayList<>(hijo1));
                if (hijo1.size() < archivo.getTamSolucion())
                    repara2puntos(individuoHijo1);

                Individuo individuoHijo2 = new Individuo(archivo, new ArrayList<>(hijo2));
                if (hijo2.size() < archivo.getTamSolucion())
                    repara2puntos(individuoHijo2);
                /*------------------------------------------------------------------*/
                
                /*UNA VEZ TENEMOS LOS 2 INDIVIDUOS HIJOS CRUZADOS (Y REPARADOS EN CASO DE TENER QUE HACERLO) ENTONCES SE AÑADEN A LA POBLACIÓN*/
                destinatario.getV_poblacion().add(individuoHijo1);
                destinatario.getV_poblacion().add(individuoHijo2);
                
            }
            /*En caos de no tener que cruzarse por probabilidad, se copian los padres al destinatario*/
            else{
                destinatario.getV_poblacion().add(origen.getV_poblacion().get(i));
                destinatario.getV_poblacion().add(origen.getV_poblacion().get(i+1));
            }
        }
    }
    
    
    private void repara2puntos(Individuo ind){
        while(ind.getCromosoma().size() < archivo.getTamSolucion()){
            double distMax = 0.0;
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
        
        for(int i = 0; i < origen.getV_poblacion().size(); i += 2){
            
            if (random.Randfloat(0,1) < config.getPROB_CRUCE()){
                
                /*--- HACEMOS EL PROCESO DE CRUCE MPX 2 VECES ---*/
                HashSet<Integer> hijo1 = new HashSet<>();
                HashSet<Integer> hijo2 = new HashSet<>();
                hijoMPX(origen.getV_poblacion().get(i),origen.getV_poblacion().get(i+1),hijo1);
                hijoMPX(origen.getV_poblacion().get(i+1),origen.getV_poblacion().get(i),hijo2);
                /*----------------------------------------------*/
                
                /*--- COMPROBAMOS QUE NO HAYA QUE REPARAR A LOS HIJOS GENERADOS ---*/
                Individuo ind1 = new Individuo(archivo, new ArrayList<>(hijo1));
                if (hijo1.size() > archivo.getTamSolucion())
                    reparaMPX(ind1);

                Individuo ind2 = new Individuo(archivo, new ArrayList<>(hijo2));
                if (hijo2.size() > archivo.getTamSolucion())
                    reparaMPX(ind2);
                /*-----------------------------------------------------------------*/
                
                /*UNA VEZ TENEMOS LOS 2 INDIVIDUOS HIJOS CRUZADOS (Y REPARADOS EN CASO DE TENER QUE HACERLO) ENTONCES SE AÑADEN A LA POBLACIÓN*/
                destinatario.getV_poblacion().add(ind1);
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
        int p;
        HashSet<Integer> probado = new HashSet<>();
        for (int k = 0; k < numPadre1; k++){
            do{
                p = random.Randint(0, padre1.size()-1);
            }while (probado.contains(p)); //EN CASO DE HABER PROBADO YA CON ESE ELEMENTO, SE REPITE EL ALEATORIO
            probado.add(p);
            hijo.add(padre1.get(p));
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
        
    private Poblacion mutacion(Poblacion origen){
        Poblacion destinatario = new Poblacion();
        int cambiar;
        boolean mutado = false;
        for(int i = 0; i < origen.getV_poblacion().size() ; i++){ //RECORRO TODOS LOS INDIVIDUOS DE LA POBLACIÓN ORIGEN
            
            /*--- POR CADA INDIVIDUO, RECORRER EL CROMOSOMA GEN A GEN Y MUTAR SI ASI INDICA EL RANDOM ---*/
            ArrayList<Integer> cromosoma_mutado = new ArrayList<>(origen.getV_poblacion().get(i).getCromosoma());
            for(int j = 0; j < cromosoma_mutado.size(); j++){ //RECORRO GEN A GEN DEL CROMOSOMA
                if (random.Randfloat(0, 1) < config.getPROB_GEN_MUTE()){ //PROB. DE QUE ESE GEN MUTE
                    cromosoma_mutado.remove(j); //EN EL CROMOSOMA MUTADO QUITAMOS EL CROMOSOMA QUE DEBE MUTAR DEL ARRAYLIST CROMOSOMA.
                    do{
                        cambiar = random.Randint(0, NUM_ELEMENTOS-1);
                    }while (cromosoma_mutado.contains(cambiar)); //REPETIR SIEMPRE Y CUANDO EL NUEVO GEN MUTADO NO EXISTA YA EN EL CROMOSOMA.
                    cromosoma_mutado.add(j,cambiar);
                    mutado = true; //CONTROLAR SI 1 GEN DEL CROMOSOMA HA SIDO MUTADO. ASÍ, SI UN CROMOSOMA NO HA MUTADO NI 1 GEN ENTONCES NO SE CUENTA LA EVALUACIÓN
                }
            }
            /*-------------------------------------------------------------------------------------------*/
            
            /*--- AÑADIR EL INDIVIDUO MUTADO O NO A LA NUEVA POBLACION ---*/
            if(mutado){
                Individuo indiv = new Individuo(archivo, cromosoma_mutado);
                mutado = false;
                destinatario.getV_poblacion().add(indiv);
            }
            else
                destinatario.getV_poblacion().add(origen.getV_poblacion().get(i));
            /*------------------------------------------------------------*/
            
        }
        return destinatario;
    }
    
    
    /*---- MÉTODO DE REEMPLAZO ----*/
    public Poblacion reemplazo(Integer k_elitismo, Poblacion p){
        Poblacion destinatario = new Poblacion();
        
        /*--- SE HACE REEMPLAZO (A PARTIR DE ELITE K) ---*/
        ArrayList<Pair<Individuo,Double>> antigua_ordenada = poblacion.ordenSegunFitness(); //ORDENO LA POBLACION P.
        ArrayList<Pair<Individuo,Double>> nueva_ordenada = p.ordenSegunFitness(); //ORDENO LA POBLACION P'
        int mayorAporte = antigua_ordenada.size()-1; //Posición del individuo de mayor aporte.
        for(int i = 0; i < config.getNUM_INDIVIDUOS(); i++){
            if (i < k_elitismo){
                destinatario.getV_poblacion().add(i, antigua_ordenada.get(mayorAporte).getKey());
                
                if (antigua_ordenada.get(mayorAporte).getValue() > MEJOR_FITNESS){
                    MEJOR_INDIVIDUO = antigua_ordenada.get(mayorAporte).getKey();
                    MEJOR_FITNESS = antigua_ordenada.get(mayorAporte).getValue();
                }
                
                mayorAporte--;
            }else{
                destinatario.getV_poblacion().add(i, nueva_ordenada.get(i).getKey());
                
                if (nueva_ordenada.get(i).getValue() > MEJOR_FITNESS){
                    MEJOR_INDIVIDUO = nueva_ordenada.get(i).getKey();
                    MEJOR_FITNESS = nueva_ordenada.get(i).getValue();
                }
                
            }
        }
        return destinatario;
    }
    
    /*MÉTODO DE EVALUACIÓN*/
    
    private void evaluacion(Poblacion p){
        /*--- CALCULAMOS EL FITNESS DE LA POBLACIÓN ---*/
        for(int i = 0; i < config.getNUM_INDIVIDUOS(); i++){ /*TODO: HACERLO SOLO PARA LOS QUE SE MODIFICAN*/
            p.getV_poblacion().get(i).costeFitness();
            evaluaciones++;
        }
    }
   
}

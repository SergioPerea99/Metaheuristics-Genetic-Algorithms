/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_pr02;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author spdlc
 */
public class SalidaDatos implements Runnable {
    /*ATRIBUTOS COMPARTIDOS PARA TODOS LOS ALGORITMOS.*/
    private final Integer semilla;
    private final ArchivoDatos archivo;
    private final StringBuilder log;
    private final CountDownLatch cdl;
    private final String[] args;

    public SalidaDatos(String[] _args, ArchivoDatos _archivo, CountDownLatch _cdl, Integer _semilla){
        archivo = _archivo;
        cdl = _cdl;
        semilla = _semilla;
        log = new StringBuilder();
        args = _args;
      
    }   
    
    @Override
    public void run() { //Método principal de cada hilo.
        long tiempoInicial, tiempoFinal;
        System.out.println("Archivo "+archivo.getNombre()+" :: Algoritmo AGG2P2E :: Nº_semilla = "+semilla);
        ArrayList<Integer> v_M;

                
        log.append("ALGORITMO GENÉTICO GENERACIONAL (CRUCE EN MPX Y ÉLITE 3) :: ARCHIVO "+archivo.getNombre()+" :: Nº_SEMILLA = "+semilla+".\n\n");
        AlgGenetico_Generacional AGG = new AlgGenetico_Generacional(args,archivo, semilla);
        log.append("ELEMENTO INICIAL DE PARTIDA:\n"+AGG.getMEJOR_INDIVIDUO().getCromosoma()+"\nCOSTE INICIAL: "+AGG.getMEJOR_FITNESS()+"\n\n");

        //Ejecución de la metaheurística.
        tiempoInicial = System.currentTimeMillis();
        AGG.ejecucionAGG();
        tiempoFinal = System.currentTimeMillis();

        //Finalización de la metahuerística.
        log.append("SOLUCIÓN FINAL:\n"+AGG.getMEJOR_INDIVIDUO().getCromosoma()+".\n\n");
        log.append("COSTE SOLUCIÓN:  "+AGG.getMEJOR_FITNESS()+".\n\n");
        log.append("DURACIÓN: " + (tiempoFinal - tiempoInicial) + " milisegundos.\n\n");
        cdl.countDown(); //Para asegurar la finalización del hilo.

    }
    
    public String getLog(){
        return log.toString();
    }
}

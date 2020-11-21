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
public class SalidaDatos {
    /*ATRIBUTOS COMPARTIDOS PARA TODOS LOS ALGORITMOS.*/
    private final Integer semilla;
    private final ArchivoDatos archivo;
    private final String algoritmo;
    private final StringBuilder log;
    private final CountDownLatch cdl;
    private final String[] args;

    public SalidaDatos(String[] _args, ArchivoDatos _archivo,String _algoritmo, CountDownLatch _cdl, Integer _semilla){
        archivo = _archivo;
        cdl = _cdl;
        semilla = _semilla;
        log = new StringBuilder();
        args = _args;
        algoritmo = _algoritmo;
        
    }   
    
//    @Override
//    public void run() { //Método principal de cada hilo.
//        long tiempoInicial, tiempoFinal;
//        System.out.println("Archivo "+archivo.getNombre()+" :: Algoritmo "+algoritmo+" :: Nº_semilla = "+semilla);
//        ArrayList<Integer> v_M;
//        switch(algoritmo){
//            
//            case "Greedy":
//                //Inicialización aleatoria de la primera solución.
//                Greedy greedy = new Greedy(args, archivo, 0);
//                log.append("ALGORITMO GREEDY :: ARCHIVO "+archivo.getNombre()+" :: Nº_SEMILLA = "+0+".\n\n");
//                v_M = new ArrayList<>(greedy.getM());
//                log.append("ELEMENTO INICIAL DE PARTIDA:\n"+greedy.getM()+"\nCOSTE INICIAL: "+greedy.costeSolucion(v_M)+"\n\n");
//                
//                //Ejecución de la metaheurística.
//                tiempoInicial = System.currentTimeMillis();
//                greedy.algoritmoGreedy();
//                tiempoFinal = System.currentTimeMillis();
//                
//                //Finalización de la metahuerística.
//                log.append("SOLUCIÓN FINAL: "+greedy.getM()+".\n\n");
//                v_M = new ArrayList<>(greedy.getM());
//                log.append("COSTE SOLUCIÓN:  "+greedy.costeSolucion(v_M)+".\n\n");
//                log.append("DURACIÓN: " + (tiempoFinal - tiempoInicial) + " milisegundos.\n\n");
//                cdl.countDown(); //Para asegurar la finalización del hilo.
//                break;
//                
//            case "Busqueda_Local":
//                //Inicialización aleatoria de la primera solución.
//                BusquedaLocal b_local = new BusquedaLocal(args, archivo, semilla);
//                log.append("BUSQUEDA LOCAL :: ARCHIVO "+archivo.getNombre()+" :: Nº_SEMILLA = "+semilla+".\n\n");
//                v_M = new ArrayList<>(b_local.getM());
//                log.append("SOLUCIÓN INICIAL DE PARTIDA:\n"+b_local.getM()+"\nCOSTE INICIAL: "+b_local.costeSolucion(v_M)+"\n\n");
//                
//                //Ejecución de la metaheurística.
//                tiempoInicial = System.currentTimeMillis();
//                b_local.algBusquedaLocal();
//                tiempoFinal = System.currentTimeMillis();
//                
//                //Finalización de la metahuerística.
//                log.append("SOLUCIÓN FINAL: "+b_local.getM()+".\n\n");
//                log.append("COSTE SOLUCIÓN:  "+b_local.getCoste()+".\n\n");
//                log.append("DURACIÓN: " + (tiempoFinal - tiempoInicial) + " milisegundos.\n\n");
//                cdl.countDown(); //Para asegurar la finalización del hilo.
//                break;
//                
//            case "Busqueda_Tabu":
//                BusquedaTabu b_tabu = new BusquedaTabu(args, archivo, semilla);
//                log.append("BUSQUEDA TABÚ :: ARCHIVO "+archivo.getNombre()+" :: Nº_SEMILLA = "+semilla+".\n\n");
//                v_M = new ArrayList<>(b_tabu.getM());
//                log.append("SOLUCIÓN INICIAL DE PARTIDA:\n"+b_tabu.getM()+"\nCOSTE INICIAL: "+b_tabu.costeSolucion(v_M)+"\n\n");
//                //Ejecución de la metaheurística.
//                tiempoInicial = System.currentTimeMillis();
//                b_tabu.algBusquedaTabu();
//                tiempoFinal = System.currentTimeMillis();
//                
//                //Finalización de la metahuerística.
//                log.append("SOLUCIÓN FINAL: "+b_tabu.getM()+".\n\n");
//                log.append("COSTE SOLUCIÓN:  "+b_tabu.getCoste()+".\n\n");
//                log.append("DURACIÓN: " + (tiempoFinal - tiempoInicial) + " milisegundos.\n\n");
//                cdl.countDown(); //Para asegurar la finalización del hilo.
//                break;
//                
//            default:
//                break;
//                    
//        }
//    }
    
//    public String getLog(){
//        return log.toString();
//    }
}

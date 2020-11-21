/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_pr02;

import java.util.ArrayList;

/**
 *
 * @author spdlc
 */
public class Poblacion {
    /*ATRIBUTOS PARA LA CARGA DE FICHEROS*/
    private final Configurador config;
    private final ArchivoDatos archivo;
    private ArrayList<Individuo> poblacion;
    
    public Poblacion(String[] _args,ArchivoDatos _archivo){
        config = new Configurador(_args[0]);
        archivo = _archivo;
        
        /*INICIALIZACIÓN DE LA POBLACIÓN*/
        while (poblacion.size() < config.getNUM_INDIVIDUOS()){
            
        }
    }
    
    
    
    
    /**
     * @return the config
     */
    public Configurador getConfig() {
        return config;
    }

    /**
     * @return the archivo
     */
    public ArchivoDatos getArchivo() {
        return archivo;
    }

    /**
     * @return the poblacion
     */
    public ArrayList<Individuo> getPoblacion() {
        return poblacion;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_pr02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author spdlc
 */
public class Configurador {
    private ArrayList<String> archivos;
    private ArrayList<Long> semillas; //Para las diferentes semillas usadas.
    private Integer MAX_ITERACIONES;
    private Integer NUM_INDIVIDUOS;
    private Integer NUM_ELITE_INDIVIDUOS;
    private String TIPO_CRUCE;
    private Float PROB_CRUCE;
    private Float PROB_GEN_MUTE;
    private Float PORCENTAJE_MPX;
    
    public Configurador(String ruta){
        archivos = new ArrayList<>();
        semillas = new ArrayList<>();
        
        String linea;
        FileReader f = null;
        try{
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            while((linea=b.readLine())!= null){
                String[] split = linea.split("="); //Dividimos la línea por iguales.
                switch(split[0]){
                    case "Archivos":
                        String[] vArchivos = split[1].split(" "); //Volvemos a dividir por espacios.
                        for (int i = 0; i < vArchivos.length; i++)
                            archivos.add(vArchivos[i]);
                        break;
                      
                    case "Semillas":
                        String[] vSemillas = split[1].split(" "); //Volvemos a dividir por espacios.
                        for (int i = 0; i < vSemillas.length; i++)
                            semillas.add(Long.parseLong(vSemillas[i]));
                        break;
                    
                    case "MaximoIteraciones":
                        MAX_ITERACIONES = Integer.parseInt(split[1]);
                        break;
                        
                    case "Num_Individuos":
                        NUM_INDIVIDUOS = Integer.parseInt(split[1]);
                        break;
                    case "Num_Elite_Individuos":
                        NUM_ELITE_INDIVIDUOS = Integer.parseInt(split[1]);
                        break;
                    case "Tipo_Cruce":
                        TIPO_CRUCE = split[1];
                        break;
                    case "Prob_Cruce":
                        PROB_CRUCE = Float.parseFloat(split[1]);
                        break;
                    case "Prob_Gen_Mutacion":
                        PROB_GEN_MUTE = Float.parseFloat(split[1]);
                        break;
                    case "Porcentaje_MPX":
                        PORCENTAJE_MPX = Float.parseFloat(split[1]);
                        break;
                    default:
                        break;
                    
                    //... (AÑADIR CASOS, SI APARECEN MÁS PARÁMETROS).
                }
                
            }
        }catch(Exception e){
            System.out.println(e);
        };
    };
    
    //GETTERS y SETTERS

    /**
     * @return the archivos
     */
    public ArrayList<String> getArchivos() {
        return archivos;
    }

    /**
     * @return the semillas
     */
    public ArrayList<Long> getSemillas() {
        return semillas;
    }

    /**
     * @param archivos the archivos to set
     */
    public void setArchivos(ArrayList<String> archivos) {
        this.archivos = archivos;
    }
    
    /**
     * @param semillas the semillas to set
     */
    public void setSemillas(ArrayList<Long> semillas) {
        this.semillas = semillas;
    }

    /**
     * @return the MAX_ITERACIONES
     */
    public Integer getMAX_ITERACIONES() {
        return MAX_ITERACIONES;
    }

    /**
     * @return the NUM_INDIVIDUOS
     */
    public Integer getNUM_INDIVIDUOS() {
        return NUM_INDIVIDUOS;
    }

    /**
     * @return the NUM_ELITE_INDIVIDUOS
     */
    public Integer getNUM_ELITE_INDIVIDUOS() {
        return NUM_ELITE_INDIVIDUOS;
    }

    /**
     * @return the TIPO_CRUCE
     */
    public String getTIPO_CRUCE() {
        return TIPO_CRUCE;
    }

    /**
     * @return the PROB_CRUCE
     */
    public Float getPROB_CRUCE() {
        return PROB_CRUCE;
    }

    /**
     * @return the PROB_GEN_MUTE
     */
    public Float getPROB_GEN_MUTE() {
        return PROB_GEN_MUTE;
    }

    /**
     * @return the PORCENTAJE_MPX
     */
    public Float getPORCENTAJE_MPX() {
        return PORCENTAJE_MPX;
    }
    
    
    
}

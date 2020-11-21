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
    private ArrayList<String> algoritmos; //Para la elección de ejecución de los algoritmos a usar.
    private ArrayList<Long> semillas; //Para las diferentes semillas usadas.
    private Integer MAX_ITERACIONES;
    private Integer INTENTOS_REINICIO;
    private Double REDUCCION_VECINDARIO;
    private Integer TENENCIA_TABU;
    private Double PROB_INTENSIFICAR_DIVERSIFICAR;
    private Integer MIN_VECINOS;
    
    public Configurador(String ruta){
        archivos = new ArrayList<>();
        algoritmos = new ArrayList<>();
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
                        //Si únicamente utilizamos un único archivo sería: archivos.add(split[1]);
                        //Como utilizaremos varios, e igual con las semillas, entonces:
                        //Si en el txt se encuentra cada archivo separado por espacios...
                        String[] vArchivos = split[1].split(" "); //Volvemos a dividir por espacios.
                        
                        //Añadimos todos los archivos al vector de archivos.
                        for (int i = 0; i < vArchivos.length; i++)
                            archivos.add(vArchivos[i]);
                        
                        break;
                        
                    case "Algoritmos":
                        String[] vAlgoritmos = split[1].split(" "); //Volvemos a dividir por espacios.
                        for (int i = 0; i < vAlgoritmos.length; i++)
                            algoritmos.add(vAlgoritmos[i]);
                        break;
                        
                    case "Semillas":
                        String[] vSemillas = split[1].split(" "); //Volvemos a dividir por espacios.
                        for (int i = 0; i < vSemillas.length; i++)
                            semillas.add(Long.parseLong(vSemillas[i]));
                        break;
                    
                    case "MaximoIteraciones":
                        MAX_ITERACIONES = Integer.parseInt(split[1]);
                        break;
                        
                    case "Intentos_Reinicio":
                        INTENTOS_REINICIO = Integer.parseInt(split[1]);
                        break;
                        
                    case "Prob_Reduccion_Vecindario":
                        REDUCCION_VECINDARIO = Double.parseDouble(split[1]);
                        break;
                        
                    case "Minimo_Vecinos":
                        MIN_VECINOS = Integer.parseInt(split[1]);
                        break;
                        
                    case "tenencia_tabu":
                        TENENCIA_TABU = Integer.parseInt(split[1]);
                        break;
                        
                    case "Prob_Intensificar_Diversificar":
                        PROB_INTENSIFICAR_DIVERSIFICAR = Double.parseDouble(split[1]);
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
     * @return the algoritmos
     */
    public ArrayList<String> getAlgoritmos() {
        return algoritmos;
    }

    /**
     * @return the semillas
     */
    public ArrayList<Long> getSemillas() {
        return semillas;
    }

    /**
     * @return the parametroExtra
     */
    public Integer getMax_Iteraciones() {
        return getMAX_ITERACIONES();
    }

    /**
     * @param archivos the archivos to set
     */
    public void setArchivos(ArrayList<String> archivos) {
        this.archivos = archivos;
    }

    /**
     * @param algoritmos the algoritmos to set
     */
    public void setAlgoritmos(ArrayList<String> algoritmos) {
        this.algoritmos = algoritmos;
    }

    /**
     * @param semillas the semillas to set
     */
    public void setSemillas(ArrayList<Long> semillas) {
        this.semillas = semillas;
    }

    /**
     * @param _maxIteraciones  the parametroExtra to set
     */
    public void setMaxIteraciones(Integer _maxIteraciones) {
        this.MAX_ITERACIONES = _maxIteraciones;
    }

    /**
     * @return the INTENTOS_REINICIO
     */
    public Integer getINTENTOS_REINICIO() {
        return INTENTOS_REINICIO;
    }

    /**
     * @return the REDUCCION_VECINDARIO
     */
    public double getREDUCCION_VECINDARIO() {
        return REDUCCION_VECINDARIO;
    }

    /**
     * @return the MAX_ITERACIONES
     */
    public Integer getMAX_ITERACIONES() {
        return MAX_ITERACIONES;
    }

    /**
     * @return the TENENCIA_TABU
     */
    public Integer getTENENCIA_TABU() {
        return TENENCIA_TABU;
    }

    /**
     * @return the PROB_INTENSIFICAR_DIVERSIFICAR
     */
    public double getPROB_INTENSIFICAR_DIVERSIFICAR() {
        return PROB_INTENSIFICAR_DIVERSIFICAR;
    }

    /**
     * @param PROB_INTENSIFICAR_DIVERSIFICAR the PROB_INTENSIFICAR_DIVERSIFICAR to set
     */
    public void setPROB_INTENSIFICAR_DIVERSIFICAR(double PROB_INTENSIFICAR_DIVERSIFICAR) {
        this.PROB_INTENSIFICAR_DIVERSIFICAR = PROB_INTENSIFICAR_DIVERSIFICAR;
    }

    /**
     * @return the MIN_VECINOS
     */
    public Integer getMIN_VECINOS() {
        return MIN_VECINOS;
    }
}

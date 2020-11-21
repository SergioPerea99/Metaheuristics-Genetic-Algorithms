/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meta_pr02;

//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author spdlc
 */
public class META_PR02 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
//        
//        Configurador config = new Configurador(args[0]);
//        System.out.println(config.getArchivos());
//        ArrayList<ArchivoDatos> archivos = new ArrayList<>();
//        for (int i = 0; i < config.getArchivos().size(); i++) {
//            archivos.add(new ArchivoDatos(config.getArchivos().get(i)));
//        }
//        
//        
//        ExecutorService ejecutor = Executors.newCachedThreadPool();
//        
//        for(int i = 0; i < config.getAlgoritmos().size(); i++){
//            for(int j = 0; j < archivos.size(); j++){
//                try {
//                    CountDownLatch cdl = new CountDownLatch(config.getSemillas().size());
//                    ArrayList<SalidaDatos> m = new ArrayList<>();
//                    switch(config.getAlgoritmos().get(i)){
//                        case "Greedy":
//                            for(int k = 0; k < config.getSemillas().size(); k++){
//                                SalidaDatos salida = new SalidaDatos(args, archivos.get(j),config.getAlgoritmos().get(i), cdl, 0);
//                                m.add(salida);
//                                ejecutor.execute(salida);
//                            }
//                            cdl.await();
//                            guardaArchivo("log/"+config.getAlgoritmos().get(i)+"_"+archivos.get(j).getNombre()+"_"+config.getSemillas().get(0)+".txt",m.get(0).getLog());
//                            
//                            break;
//                        case "Busqueda_Local":
//                            for(int k = 0; k < config.getSemillas().size(); k++){
//                                SalidaDatos salida = new SalidaDatos(args, archivos.get(j),config.getAlgoritmos().get(i), cdl, k);
//                                m.add(salida);
//                                ejecutor.execute(salida);
//                            }
//                            cdl.await();
//                            for(int k = 0; k < m.size(); k++){
//                                guardaArchivo("log/"+config.getAlgoritmos().get(i)+"_"+archivos.get(j).getNombre()+"_"+config.getSemillas().get(k)+".txt",m.get(k).getLog());
//                            }
//                            break;
//                        case "Busqueda_Tabu":
//                            
//                            for(int k = 0; k < config.getSemillas().size(); k++){
//                                SalidaDatos salida = new SalidaDatos(args, archivos.get(j),config.getAlgoritmos().get(i), cdl, k);
//                                m.add(salida);
//                                ejecutor.execute(salida);
//                            }
//                            cdl.await();
//                            for(int k = 0; k < m.size(); k++){
//                                guardaArchivo("log/"+config.getAlgoritmos().get(i)+"_"+archivos.get(j).getNombre()+"_"+config.getSemillas().get(k)+".txt",m.get(k).getLog());
//                            }
//                            break;
//                        default:
//                            break;
//                    }
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(META_PR01.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//            }
//        }
//        
    }
//    
//    public static void guardaArchivo(String ruta,String texto){
//        FileWriter fichero = null;
//        PrintWriter pw = null;
//        try{
//            fichero = new FileWriter(ruta);
//            pw = new PrintWriter(fichero);
//            
//            pw.print(texto);
//            
//        }catch(IOException e1){
//        } finally {
//            try{
//                if(fichero != null)
//                    fichero.close();
//            }catch(IOException e2){
//            }
//        }
//    }
    
}

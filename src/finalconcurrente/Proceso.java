/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.jblas.DoubleMatrix;

/**
 *
 * @author steve07-ultra
 */
public class Proceso extends Thread{
    final private String id;
    final private DoubleMatrix transiciones;
    final private Monitor monitor;
    final private PanelControl panel;
    private DoubleMatrix marcaConseguida;
    
    public Proceso(String id, Monitor monitor, PanelControl panel){
        this.id=id;
        this.transiciones=this.leer_transiciones();
        System.out.println(transiciones);
        this.monitor=monitor;
        this.panel=panel;
        this.marcaConseguida = null;
    }
    
    public final DoubleMatrix leer_transiciones(){
        BufferedReader in;
        int nroLinea=0;
        double [] trans = null;
        try {
            String filepath = this.id+".txt";
            in = new BufferedReader(new FileReader(filepath));
            String line;
            while((line = in.readLine()) !=null) {
                nroLinea++;
                if(nroLinea==1){
                    trans=new double[Integer.parseInt(line)];
                }
                else if(nroLinea==2){
                    String [] tokens = line.split(" ");
                    for (String token : tokens) {
                        trans[Integer.parseInt(token)] = 1.0;
                    }
                }
            }
            try{
                in.close();
                }
            catch(IOException exc){
                System.out.println("No pude cerrar el archivo abierto.");
                System.out.println(exc.getMessage());
                }
        } 
        
        catch (IOException exc) {
            System.out.println("No se pudo abrir el archivo o bien se exedieron los límites de la memoria");
            System.out.println(exc.getMessage());
        }
        return new DoubleMatrix(trans) ;
    }
    
    @Override
    public void run(){
        super.run();
        while(true){
            try{
                marcaConseguida=monitor.solicitarDisparo(transiciones);
                //dispAutorizado=monitor.solicitarDisparo(transiciones);
                System.out.println(this.getName()+" --> Actualiza Panel de Control con:\n"+marcaConseguida);
                Thread.sleep(1500);
                this.panel.actualizar(marcaConseguida);
                
            }
           catch(InterruptedException e){
               e.getLocalizedMessage();
           }
        }
    }
}
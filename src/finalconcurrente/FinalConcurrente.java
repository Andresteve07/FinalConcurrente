/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package finalconcurrente;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author steve07-ultra
 */
public class FinalConcurrente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PanelControl panel=new PanelControl();
        VentanaPanel ventana = new VentanaPanel("Panel de control de Simulación de Celda Flexible",panel);
        ventana.mostrar();
        Monitor monitor=new Monitor("matrizIncidencia.txt","marcaInicial.txt");
        Proceso p1 = new Proceso("Proceso_1",monitor);
        Proceso p2 = new Proceso("Proceso_2",monitor);
        Proceso p3 = new Proceso("Proceso_3",monitor);
        Actualizador actualizador;
        try {
            actualizador = new Actualizador(panel);
            actualizador.start();
        } catch (IOException ex) {
            Logger.getLogger(FinalConcurrente.class.getName()).log(Level.SEVERE, null, ex);
        }
        p1.start();
        p2.start();
        p3.start();
    }
    
}

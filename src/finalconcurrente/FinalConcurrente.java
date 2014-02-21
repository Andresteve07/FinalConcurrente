/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package finalconcurrente;

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
        Proceso p1 = new Proceso("Proceso_1",monitor,panel);
        Proceso p2 = new Proceso("Proceso_2",monitor,panel);
        Proceso p3 = new Proceso("Proceso_3",monitor,panel);
        p1.start();
        p2.start();
        p3.start();
    }
    
}

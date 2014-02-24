/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jblas.DoubleMatrix;

/**
 *
 * @author steve07-ultra
 */
public class PanelControl extends JPanel{
    private final ArrayList<Componente> listaComponentes;
    public PanelControl(){//Lo podría hacer desde un archivo para ser más generico, por ahora vamos así.
        this.setOpaque(false);
        listaComponentes=new ArrayList<>();
        for(int i=0;i<15;i++){
            if(i==0||i==2||i==4||i==10||i==12||i==14){
                Almacen al=new Almacen();
                listaComponentes.add(al);
                this.add(al);
            }
            else if(i==6||i==8){
                this.add(new JLabel());
            }
            else{
                Recurso comp=new Recurso();
                listaComponentes.add(comp);
                this.add(comp);
            }
        }
        this.setLayout(new GridLayout(3,5));
        
    }
    public void actualizar(DoubleMatrix marcaActual){
        listaComponentes.get(0).fijarEstado(new Integer((int)marcaActual.get(20,0)));//03
        listaComponentes.get(1).fijarEstado(new Integer((int)marcaActual.get(2,0)));//M3
        listaComponentes.get(2).fijarEstado(new Integer((int)marcaActual.get(22,0)));//02
        listaComponentes.get(3).fijarEstado(new Integer((int)marcaActual.get(3,0)));//M4
        listaComponentes.get(4).fijarEstado(new Integer((int)marcaActual.get(19,0)));//O1
        listaComponentes.get(5).fijarEstado(new Integer((int)marcaActual.get(28,0)));//R1
        listaComponentes.get(6).fijarEstado(new Integer((int)marcaActual.get(29,0)));//R2
        listaComponentes.get(7).fijarEstado(new Integer((int)marcaActual.get(30,0)));//R3
        listaComponentes.get(8).fijarEstado(new Integer((int)marcaActual.get(4,0)));//I1
        listaComponentes.get(9).fijarEstado(new Integer((int)marcaActual.get(0,0)));//M1
        listaComponentes.get(10).fijarEstado(new Integer((int)marcaActual.get(9,0)));//I2
        listaComponentes.get(11).fijarEstado(new Integer((int)marcaActual.get(1,0)));//M2
        listaComponentes.get(12).fijarEstado(new Integer((int)marcaActual.get(25,0)));//I3        
        for(Componente com : listaComponentes){
            System.out.println(com.obtenerEstado());
        }
    }  
}

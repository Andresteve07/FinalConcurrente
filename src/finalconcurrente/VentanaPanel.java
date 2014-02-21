/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author steve07-ultra
 */
public class VentanaPanel extends JFrame{
    public VentanaPanel(String titulo) {
    
        PanelControl panel = new PanelControl();
        this.getContentPane().add(panel,BorderLayout.CENTER);
        this.fijarParametros(titulo);
        
  }
    public VentanaPanel(String titulo, PanelControl panel) {
    
        this.getContentPane().add(panel,BorderLayout.CENTER);
        this.fijarParametros(titulo);
        
        
  }
    private void fijarParametros(String titulo){
        this.setSize(650,350);/*@\label{setsize:line}@*/
        this.setLocation(200,200);/*@\label{setloc:line}@*/
        this.setTitle(titulo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);/*@\label{closeOp:line}@*/
    }
    public void mostrar(){
        this.setVisible(true);
    }
}

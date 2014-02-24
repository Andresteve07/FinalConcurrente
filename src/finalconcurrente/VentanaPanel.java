/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author steve07-ultra
 */
public class VentanaPanel extends JFrame{
    public VentanaPanel(String titulo, PanelControl panel) {
        //this.setContentPane(new JLabel(new ImageIcon("fondo.png")));
        //this.getContentPane().add(panel,BorderLayout.CENTER);
        //this.add(panel, BorderLayout.CENTER);
        this.fijarParametros(titulo);
        this.setSize(640,310);
        BufferedImage imagenFondo=null;
        try {
            imagenFondo = ImageIO.read(new File("fondoR.png"));
        } catch (IOException ioexc) {
            System.out.println(ioexc.getLocalizedMessage());
        }
        Image imagenDim1 = imagenFondo.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon iconoFondo = new ImageIcon(imagenDim1);
        JLabel fondo=new JLabel(iconoFondo,JLabel.CENTER);
        //fondo.setSize(605, 305);
        this.add(fondo);
        fondo.setLayout(new BorderLayout());
        JLabel espacio=new JLabel("  ");
        espacio.setSize(10, fondo.getHeight());
        fondo.add(espacio,BorderLayout.WEST);
        fondo.add(panel,BorderLayout.CENTER);
        //this.setSize(fondo.getWidth(), fondo.getHeight());
  }
    
    private void fijarParametros(String titulo){
        this.setSize(640,310);/*@\label{setsize:line}@*/
        this.setLocation(200,200);/*@\label{setloc:line}@*/
        this.setTitle(titulo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);/*@\label{closeOp:line}@*/
        //setVisible(true);
    }
    public void mostrar(){
        this.setVisible(true);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package finalconcurrente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author steve07-ultra
 */
public class Almacen extends JPanel implements Componente{
    private Integer estado;
    private final JLabel etiqSup;
    private final JPanel panelNumerico;
    private final JLabel etiqUnidad;
    private final JLabel etiqDecena;
    private BufferedImage imagenUnidad = null;
    private BufferedImage imagenDecena = null;
    private ImageIcon iconoUnidad;
    private ImageIcon iconoDecena;

    public Almacen() {
        this.setOpaque(false);
        //modelo=new ModeloSemáforo();
        //valueLabel = new JLabel(""+counter.getValue(),SwingConstants.CENTER);
        estado = new Integer(0);
        etiqSup = new JLabel(" ");
        //etiqSup.setSize(90, 20);
        etiqSup.setOpaque(false);
        try {
            imagenUnidad = ImageIO.read(new File("nulo.png"));
            imagenDecena = ImageIO.read(new File("nulo.png"));
        } catch (IOException ioexc) {
            System.out.println(ioexc.getLocalizedMessage());
        }
        Image imagenDim1 = imagenUnidad.getScaledInstance(45, 62, Image.SCALE_SMOOTH);
        iconoUnidad = new ImageIcon(imagenDim1);
        etiqUnidad = new JLabel(iconoUnidad, JLabel.CENTER);
        Image imagenDim2 = imagenDecena.getScaledInstance(45, 62, Image.SCALE_SMOOTH);
        iconoDecena = new ImageIcon(imagenDim2);
        etiqDecena = new JLabel(iconoDecena, JLabel.CENTER);
        etiqUnidad.setOpaque(false);
        etiqDecena.setOpaque(false);
        panelNumerico=new JPanel();
        panelNumerico.setLayout(new GridLayout(1,2));
        panelNumerico.add(etiqDecena);
        panelNumerico.add(etiqUnidad);
        //panelNumerico.setSize(90, 50);
        panelNumerico.setOpaque(false);
        
        JLabel espIzq=new JLabel("    ");
        espIzq.setOpaque(false);
        JLabel espDer=new JLabel("    ");
        espDer.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.add(etiqSup, BorderLayout.NORTH);
        this.add(espIzq,BorderLayout.WEST);
        this.add(panelNumerico,BorderLayout.CENTER);
        this.add(espDer,BorderLayout.EAST);
        //this.setLayout(new GridLayout(2,1));

    }

    @Override
    public void actualizarApariencia() {
        Integer decena=(int)(estado/10);
        Integer unidad=(estado%10);
        try {
            if (estado >= 0) {
                imagenDecena = ImageIO.read(new File(decena.toString()+"A.png"));
                imagenUnidad = ImageIO.read(new File(unidad.toString()+"A.png"));
            } else{
                imagenDecena = ImageIO.read(new File("E.png"));
                imagenUnidad = ImageIO.read(new File("E.png"));
            }
        } catch (IOException ioexc) {
            System.out.println(ioexc.getLocalizedMessage());
        }
        Image imagenDim1 = imagenUnidad.getScaledInstance(45, 62, Image.SCALE_SMOOTH);
        iconoUnidad = new ImageIcon(imagenDim1);
        etiqUnidad.setIcon(iconoUnidad);
        Image imagenDim2 = imagenDecena.getScaledInstance(45, 62, Image.SCALE_SMOOTH);
        iconoDecena = new ImageIcon(imagenDim2);
        etiqDecena.setIcon(iconoDecena);
    }

    @Override
    public void fijarEstado(Integer estado) {
        if (this.estado != estado) {
            this.estado = estado;
            this.actualizarApariencia();
        }
    }

    @Override
    public int obtenerEstado() {
        return estado;
    }
}

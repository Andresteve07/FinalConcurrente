/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.awt.BorderLayout;
import java.awt.Component;
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
public class Recurso extends JPanel implements Componente{

    private Integer estado;
    private final JLabel etiquetaSup;
    private final JLabel etiquetaCantTok;
    private final JLabel etiquetaIndicador;
    private BufferedImage imagenCantTok = null;
    private BufferedImage imagenIndicador = null;
    private ImageIcon iconoCantTok;
    private ImageIcon iconoIndicador;

    public Recurso() {
        this.setOpaque(false);
        estado = new Integer(0);
        etiquetaSup = new JLabel(" ");
        etiquetaSup.setSize(104, 20);
        etiquetaSup.setOpaque(false);
        try {
            imagenCantTok = ImageIO.read(new File("nulo.png"));
            imagenIndicador = ImageIO.read(new File("indicadorAzul.png"));
        } catch (IOException ioexc) {
            System.out.println(ioexc.getLocalizedMessage());
        }
        Image imagenDim1 = imagenCantTok.getScaledInstance(45, 62, Image.SCALE_SMOOTH);
        iconoCantTok = new ImageIcon(imagenDim1);
        etiquetaCantTok = new JLabel(iconoCantTok, JLabel.CENTER);
        Image imagenDim2 = imagenIndicador.getScaledInstance(65, 65, Image.SCALE_SMOOTH);
        iconoIndicador = new ImageIcon(imagenDim2);
        etiquetaIndicador = new JLabel(iconoIndicador, JLabel.CENTER);
        this.setLayout(new BorderLayout());
        etiquetaCantTok.setOpaque(false);
        etiquetaIndicador.setOpaque(false);
        this.add(etiquetaSup, BorderLayout.NORTH);
        this.add(etiquetaCantTok, BorderLayout.WEST);
        this.add(etiquetaIndicador, BorderLayout.CENTER);
        //this.setLayout(new GridLayout(2,1));

    }

    @Override
    public void actualizarApariencia() {
        try {
            if (estado > 0) {
                imagenIndicador = ImageIO.read(new File("indicadorVerde.png"));
                imagenCantTok = ImageIO.read(new File(estado.toString() + ".png"));
            } else if (estado == 0) {
                imagenIndicador = ImageIO.read(new File("indicadorAzul.png"));
                imagenCantTok = ImageIO.read(new File("0.png"));
            } else {
                imagenIndicador = ImageIO.read(new File("indicadorRojo.png"));
                imagenCantTok = ImageIO.read(new File("E.png"));
            }
        } catch (IOException ioexc) {
            System.out.println(ioexc.getLocalizedMessage());
        }
        Image imagenDim1 = imagenCantTok.getScaledInstance(45, 62, Image.SCALE_SMOOTH);
        iconoCantTok = new ImageIcon(imagenDim1);
        etiquetaCantTok.setIcon(iconoCantTok);
        Image imagenDim2 = imagenIndicador.getScaledInstance(65, 65, Image.SCALE_SMOOTH);
        iconoIndicador = new ImageIcon(imagenDim2);
        etiquetaIndicador.setIcon(iconoIndicador);
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

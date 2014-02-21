/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author steve07-ultra
 */
public class Componente extends JPanel{
    private Integer estado;
    private JLabel etiqueta;
    private JLabel etiquetaCant;
    public Componente(String nombre, String cant){
        //modelo=new ModeloSemáforo();
        //valueLabel = new JLabel(""+counter.getValue(),SwingConstants.CENTER);
        estado=new Integer(-1);
        etiqueta=new JLabel(""+nombre,SwingConstants.CENTER);
        etiquetaCant=new JLabel(""+cant,SwingConstants.CENTER);
        //Border borde=BorderFactory.createLineBorder(Color.WHITE,2);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setBackground(Color.BLACK);
        etiqueta.setSize(30,20);
        etiqueta.setOpaque(true);
        //etiqueta.setBorder(borde);
        etiquetaCant.setBackground(Color.BLACK);
        etiquetaCant.setForeground(Color.WHITE);
        etiquetaCant.setSize(30,20);
        etiquetaCant.setOpaque(true);
        //etiquetaCant.setBorder(borde);
        //this.setBackground(Color.blue);
        this.add(etiqueta);
        this.add(etiquetaCant);
        this.setLayout(new GridLayout(2,1));

    }
    public void fijarEstado(Integer estado){
        this.estado=estado;
        this.actualizarApariencia();
    }
    public int obtenerEstado(){
        return estado;
    }
    public void actualizarApariencia(){
        if(estado<0){
            etiqueta.setBackground(Color.gray);
            etiquetaCant.setBackground(Color.gray);
            etiquetaCant.setText(""+estado.toString());
        }
        else{
            switch(estado){
                    case 0:
                        etiqueta.setBackground(Color.red);
                        etiquetaCant.setBackground(Color.red);
                        etiquetaCant.setText(""+estado.toString());
                        break;                    
                    default:
                        etiqueta.setBackground(Color.GREEN);
                        etiquetaCant.setBackground(Color.GREEN);
                        etiquetaCant.setText(""+estado.toString());
                        break;            
            }
        }
    }
    
}
/*
 if(estado>0){
            etiqueta.setBackground(Color.green);
            etiquetaCant.setBackground(Color.green);
            etiquetaCant.setText(""+estado.toString());
        }
        else{
            if(estado==0){
                etiqueta.setBackground(Color.red);
                etiquetaCant.setBackground(Color.red);
                etiquetaCant.setText(""+estado.toString());
            }
            else{
                etiqueta.setBackground(Color.black);
                etiquetaCant.setBackground(Color.black);
                etiquetaCant.setText("");
            }
        }
 */
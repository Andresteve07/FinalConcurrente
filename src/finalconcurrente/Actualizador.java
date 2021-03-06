/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package finalconcurrente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jblas.DoubleMatrix;

/**
 *
 * @author steve07-ultra
 */
public class Actualizador extends Thread{
    final private PanelControl panel;
    private ServerSocket socketServidor;
    private Socket socket = null;
    private ObjectInputStream flujoEntrada = null;
    public Actualizador(PanelControl panel) throws IOException{
        this.panel=panel;
        this.socketServidor=new ServerSocket(4445);
    }
    public void comunicar(){
        try{
            while(true){
                    socket = socketServidor.accept();
                    //System.out.println("Actualizador Conectado");
                    flujoEntrada = new ObjectInputStream(socket.getInputStream());

                    DoubleMatrix matriz= (DoubleMatrix) flujoEntrada.readObject();
                    //System.out.println("Nueva marca recibida =\n" + matriz);
                    //System.out.println("Nueva marca recibida =\n" + matriz.get(22,0));
                    panel.actualizar(matriz);
                    socket.close();
                }
        }
        catch (SocketException sExc) {
            Logger.getLogger(FinalConcurrente.class.getName()).log(Level.SEVERE, null, sExc);    
            System.exit(0);
                
        } catch (IOException | ClassNotFoundException exc) {
                Logger.getLogger(FinalConcurrente.class.getName()).log(Level.SEVERE, null, exc);
        }
    }
    @Override
    public void run(){
        //super.run();
        this.comunicar();
    }
}

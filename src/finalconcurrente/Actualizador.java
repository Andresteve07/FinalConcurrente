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
public class Actualizador extends Thread{
    private final Monitor monitor;
    
    public Actualizador(Monitor monitor){
        this.monitor=monitor;
    }
    
    @Override
    public void run(){
        super.run();
        while(true){
            try{
                this.monitor.actualizarInterfaz();
            }
            catch(InterruptedException iexc){
                System.out.println(iexc.getMessage());
            }
        }
    }
}

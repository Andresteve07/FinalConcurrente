/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jblas.DoubleMatrix;

/**
 *
 * @author steve07-ultra
 */
public class Monitor {
    final private PanelControl panel;
    DoubleMatrix matrizIncidenciaPrevia;
    DoubleMatrix matrizIncidenciaPosterior;
    DoubleMatrix marcaActual;
    private final Lock acceso = new ReentrantLock();
    private final Condition ejecucionDisp =acceso.newCondition();
    private final Condition cambioMarca = acceso.newCondition();
    private int cantDisp;
    
    //DoubleMatrix transAutomaticas;
    
    public Monitor(String nombreArchMatIncPre, String nombreArchMatIncPos, String nombreArchivoMarcIn, PanelControl panel){
        matrizIncidenciaPrevia=this.leerMatriz(nombreArchMatIncPre);
        matrizIncidenciaPosterior=this.leerMatriz(nombreArchMatIncPos);
        //System.out.println(matrizIncidencia);
        //tranSencibilizadas=new DoubleMatrix();
        marcaActual=this.leerMarca(nombreArchivoMarcIn);
        System.out.println(marcaActual);
        //System.out.println(marcaActual);
        //for(int i=0;i<matrizIncidencia.columns;i++){
        //    listaSemaforos.add(new Semaphore(1));
        //}
        cantDisp=0;
        this.panel=panel;
    }
    private DoubleMatrix leerMatriz(String nombreArchivo){
        BufferedReader in;
        int rows = 0;
        int columns=0;
        double [][] matrix = null;
        try {
            //String filepath = "archivo.txt";
            int lineNum = 0;

            int row=0;
            in = new BufferedReader(new FileReader(nombreArchivo));
            String line;
            while((line = in.readLine()) !=null) {
                lineNum++;
                if(lineNum==1) {
                    rows = Integer.parseInt(line);
                    //System.out.println("The number of rows of the matrix are: " + rows);
                } else if(lineNum==2) {
                    columns = Integer.parseInt(line);
                    //System.out.println("The number of columns of the matrix are: " + columns);
                    matrix = new double[rows][columns];
                } else {
                    String [] tokens = line.split(" ");
                    for (int j=0; j<tokens.length; j++) {
                        //System.out.println("I am filling the row: " + row);
                        matrix[row][j] = Double.parseDouble(tokens[j]);
                    }
                    row++;
                }
            }
            try{
                in.close();
                }
            catch(Exception exc){
                System.out.println("No pude cerrar el archivo abierto.");
                }
        } 
        
        catch (Exception ex) {
            System.out.println("The code throws an exception");
            System.out.println(ex.getMessage());
        } 
        return  new DoubleMatrix(matrix);
    }
    private DoubleMatrix leerMarca(String nombreArchivo){
        BufferedReader in;
        double [] vector = null;
        try {
            //String filepath = args[0];
            String filepath = nombreArchivo;
            in = new BufferedReader(new FileReader(filepath));
            String line;
            while((line = in.readLine()) !=null) {
                    String [] tokens = line.split(" ");
                    vector = new double[tokens.length];
                    for (int j=0; j<tokens.length; j++) {                      
                        vector[j] = Double.parseDouble(tokens[j]);
                    }
            }
            try{
                in.close();
                }
            catch(IOException exc){
                System.out.println("No pude cerrar el archivo abierto.");
                }
        } 
        
        catch (IOException exc) {
            System.out.println("No se pudo abrir el archivo o bien se exedieron los límites de la memoria");
            System.out.println(exc.getMessage());
        }
        return new DoubleMatrix(vector) ;
    }
    public LinkedList<DoubleMatrix> verificarDisparo(DoubleMatrix disparosRequeridos){
        //-----------------------------------------------DETERMINACIÓN DE POSIBLES DISPAROS--------------------------------------------------------------------------------        
        //disparosRequeridos=disparosRequeridos.or(transAutomaticas);
        LinkedList<DoubleMatrix> disparosPosibles = new LinkedList<>();
        //if(disparosRequeridos.columns!=matrizIncidencia.columns)
            //throw new Exception("El vector de disparos solicitados no tiene el tamanio correcto"+disparosRequeridos.columns);
        for(int i=0;i<disparosRequeridos.rows;i++){//Para cada una de las posiciones del vector de disparos requeridos...
            if(disparosRequeridos.get(i,0)==1){//Si se requiere el disparo de la transición (i)...
                double[] vectorDisparo=new double[disparosRequeridos.rows];//genero un vector lleno de (0.0)
                vectorDisparo[i]=1.0;//coloco un 1.0 en la posicion que corresponde a la transicion que deseo disparar.
                DoubleMatrix disparo= new DoubleMatrix(vectorDisparo);//genero un vector vertical de disparo con los valores antes cargados.De 21x1
                //DoubleMatrix columnai=matrizIncidencia.getColumn(i);
                //System.out.println(columnai);
                DoubleMatrix marcaHipotetica=matrizIncidenciaPrevia.mmul(disparo).rsub(marcaActual);//Se genera la marca hipotética de la red si se disparara esa transición.
                System.out.println(marcaHipotetica);
                DoubleMatrix controlHabilitacion= marcaHipotetica.ge(0.0);//Se genera un vector vertical de control de marca mayor o igual a 0.0 por plaza.
                //System.out.println(controlHabilitacion);
                //double[] vectorUNOS=new double[disparosRequeridos.columns];
                //Arrays.fill(vectorUNOS,1);
                DoubleMatrix patronUNOS=DoubleMatrix.ones(controlHabilitacion.rows, controlHabilitacion.columns);//Se genera un vector vertical lleno de 1.0 para realizar la comprobación de disparo permitido
                if(controlHabilitacion.equals(patronUNOS)){//Se compara el vector de control con el patron de UNOS para saber si es posible realizar el disparo.
                    System.out.println("Disparo válido para la transicion: "+i);
                    //disparosPosibles=disparosPosibles.add(disparo);
                    disparosPosibles.add(disparo);   
                }
            }
        }
        return disparosPosibles;
    }
    
    public DoubleMatrix adquirirRecurso(DoubleMatrix disparosRequeridos)throws InterruptedException{
        acceso.lock();
        //DoubleMatrix disparosPosibles=DoubleMatrix.zeros(1, matrizIncidencia.columns);        
        System.out.println("Entré al monitor soy"+Thread.currentThread().getName());
//-----------------------------------------------PROTOCOLO DE DISPARO--------------------------------------------------------------------------------
        LinkedList<DoubleMatrix> disparosPosibles = verificarDisparo(disparosRequeridos);
        try{
            while(disparosPosibles.isEmpty()){//Si existe al menos UN disparo posible para este proceso...
                System.out.println("Me voy a dormir soy"+Thread.currentThread().getName());
                ejecucionDisp.await();
                System.out.println("Volví del await() soy"+Thread.currentThread().getName());
                disparosPosibles=verificarDisparo(disparosRequeridos);
            }
            DoubleMatrix disparoPermitido=disparosPosibles.get(new Random().nextInt(disparosPosibles.size()));
            marcaActual=matrizIncidenciaPrevia.mmul(disparoPermitido).rsub(marcaActual);
            //Se dispara uno aleatorio sobre la lista de disparos posibles para este proceso
            System.out.println("Hago signalAll() soy"+Thread.currentThread().getName());
            this.cantDisp++;
            System.out.println(cantDisp);
            ejecucionDisp.signalAll();//Se notifica el cambio de marca ya que este puede haber habilitado otros disparos para este u otros procesos.
            return disparoPermitido;
        }
        finally{
            System.out.println("Devuelvo el lock soy"+Thread.currentThread().getName());
            acceso.unlock();
        }
    }
    public void devolverRecurso(DoubleMatrix disparoObligado)throws InterruptedException{
        acceso.lock();
        //DoubleMatrix disparosPosibles=DoubleMatrix.zeros(1, matrizIncidencia.columns);        
        try{
            System.out.println("Entré al monitor para DEVOLVER soy"+Thread.currentThread().getName());
            marcaActual=marcaActual.add(matrizIncidenciaPosterior.mmul(disparoObligado));//Posterior
            //Se dispara uno aleatorio sobre la lista de disparos posibles para este proceso        
            this.cantDisp++;
            System.out.println(cantDisp);
            System.out.println("Hago signalAll() soy"+Thread.currentThread().getName());
            cambioMarca.signal();
            ejecucionDisp.signalAll();//Se notifica el cambio de marca ya que este puede haber habilitado otros disparos para este u otros procesos.
            
        }
        finally{
            System.out.println("Devuelvo el lock soy"+Thread.currentThread().getName());
            acceso.unlock();
        }
    }
    
    public void actualizarInterfaz() throws InterruptedException{
        acceso.lock();
        try{
            cambioMarca.await();
            this.panel.actualizar(marcaActual);
            }
        finally{
            acceso.unlock();
        }
    }
}
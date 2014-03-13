/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalconcurrente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jblas.DoubleMatrix;

/**
 *
 * @author steve07-ultra
 */
public class Monitor {
    DoubleMatrix matrizIncidencia;
    DoubleMatrix marcaActual;
    private final Lock acceso = new ReentrantLock();
    private final Condition ejecucionDisp=acceso.newCondition();
    private int cantDisp;
    FileWriter archivo = null;
    PrintWriter pw = null;
    LinkedList<DoubleMatrix> datos;
    String nombreArchSal;
    //DoubleMatrix transAutomaticas;
    
    public Monitor(String nombreArchivoMatInc, String nombreArchivoMarcIn, String nombreArchSal){
        try{
            datos=new LinkedList<>();
            matrizIncidencia=this.leerMatriz(nombreArchivoMatInc);
            //System.out.println(matrizIncidencia);
            //tranSencibilizadas=new DoubleMatrix();
            marcaActual=this.leerMarca(nombreArchivoMarcIn);
            //System.out.println(marcaActual);
            //for(int i=0;i<matrizIncidencia.columns;i++){
            //    listaSemaforos.add(new Semaphore(1));
            //}
            cantDisp=0;
            this.nombreArchSal=nombreArchSal;
            //archivo = new FileWriter(nombreArchSal);
            //archivo.close();
            File salidaVieja=new File(nombreArchSal);
            if(salidaVieja.exists())
                salidaVieja.delete();
            Files.copy(new File("encabezado.html").toPath(),new File(nombreArchSal).toPath());
        }
        catch(IOException excp){
            System.out.println("Ahhh");
        }
        
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
                    System.out.println("The number of rows of the matrix are: " + rows);
                } else if(lineNum==2) {
                    columns = Integer.parseInt(line);
                    System.out.println("The number of columns of the matrix are: " + columns);
                    matrix = new double[rows][columns];
                } else {
                    String [] tokens = line.split(" ");
                    System.out.println(tokens.length);
                    for (int j=0; j<tokens.length; j++) {
                        System.out.println("I am filling the row: " + row);
                        matrix[row][j] = Double.parseDouble(tokens[j]);
                    }
                    row++;
                }
            }
            try{
                in.close();
                }
            catch (IOException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        catch (IOException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        } 
        /*
        System.out.println("I am printing the matrix: ");
        for (int i=0; i < rows; i++) {
            for(int j=0; j < columns; j++)
                System.out.print(matrix[i][j]+"\t");
            System.out.println("");
        }*/
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
                DoubleMatrix marcaHipotetica=marcaActual.add(matrizIncidencia.mmul(disparo));//Se genera la marca hipotética de la red si se disparara esa transición.
                DoubleMatrix controlHabilitacion= marcaHipotetica.ge(0.0);//Se genera un vector vertical de control de marca mayor o igual a 0.0 por plaza.
                //System.out.println(controlHabilitacion);
                //double[] vectorUNOS=new double[disparosRequeridos.columns];
                //Arrays.fill(vectorUNOS,1);
                DoubleMatrix patronUNOS=DoubleMatrix.ones(controlHabilitacion.rows, controlHabilitacion.columns);//Se genera un vector vertical lleno de 1.0 para realizar la comprobación de disparo permitido
                if(controlHabilitacion.equals(patronUNOS)){//Se compara el vector de control con el patron de UNOS para saber si es posible realizar el disparo.
                    //System.out.println("Disparo válido para la transicion: "+i);
                    //disparosPosibles=disparosPosibles.add(disparo);
                    disparosPosibles.add(disparo);   
                }
            }
        }
        return disparosPosibles;
    }
    
    public LinkedList<DoubleMatrix> solicitarDisparo(DoubleMatrix disparosRequeridos)throws InterruptedException{
        acceso.lock();
        //DoubleMatrix disparosPosibles=DoubleMatrix.zeros(1, matrizIncidencia.columns);        
        //System.out.println("Entré al monitor soy"+Thread.currentThread().getName());
//-----------------------------------------------PROTOCOLO DE DISPARO--------------------------------------------------------------------------------
        LinkedList<DoubleMatrix> disparosPosibles = verificarDisparo(disparosRequeridos);
        try{
            while(disparosPosibles.isEmpty()){//Si existe al menos UN disparo posible para este proceso...
                ejecucionDisp.await();
                //System.out.println("Volví del await() soy"+Thread.currentThread().getName());
                disparosPosibles=verificarDisparo(disparosRequeridos);
            }
            DoubleMatrix disparoElegido=disparosPosibles.get(new Random().nextInt(disparosPosibles.size()));
            datos.add(0,disparoElegido);
            marcaActual=marcaActual.add(matrizIncidencia.mmul(disparoElegido));
            datos.add(1,marcaActual);
            this.imprimirDatos(datos);
            //Se dispara uno aleatorio sobre la lista de disparos posibles para este proceso
            //System.out.println("Hago signalAll() soy"+Thread.currentThread().getName());
            this.cantDisp++;
            System.out.println("Cantidad Disparos: "+cantDisp);
            ejecucionDisp.signalAll();//Se notifica el cambio de marca ya que este puede haber habilitado otros disparos para este u otros procesos.
            return this.datos;
        }
        finally{
            //System.out.println("Devuelvo el lock soy"+Thread.currentThread().getName());
            acceso.unlock();
        }
    }
    void imprimirDatos(LinkedList<DoubleMatrix> datos){
        try {
            DoubleMatrix testigo;
            testigo=matrizIncidencia.mmul(datos.get(0));
            //DoubleMatrix patronCeros=DoubleMatrix.zeros(testigo.rows, testigo.columns);
            archivo = new FileWriter(nombreArchSal,true);
            pw = new PrintWriter(archivo);
            pw.print("<tr>");
            pw.print("<th>"+Thread.currentThread().getName()+"</th>");
            for(int k=0;k<2;k++){
                    for(int i=0;i<datos.get(k).columns;i++){
                        for(int j=0;j<datos.get(k).rows;j++){
                            switch(k){
                                case 0:
                                    if(((int)datos.get(k).get(j,i))>0)
                                        pw.print("<td style=\"background-color: yellow;\">");
                                    else
                                        pw.print("<td>");
                                    break;
                                case 1:
                                        if(((int)testigo.get(j,i))!=0)
                                            pw.print("<td style=\"background-color: yellow;\">");
                                        else
                                            pw.print("<td>");
                                    break;
                                default:
                                    pw.print("<td>");
                            }
                            pw.print((int)datos.get(k).get(j,i));
                            pw.print("</td>");
                        }
                    }
                    if(k==0)
                        pw.print("<td>&nbsp;</td>");
            }   
            pw.print("</tr>\n");
        }
        
        catch (IOException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            try {
               // Nuevamente aprovechamos el finally para 
               // asegurarnos que se cierra el fichero.
               if (null !=archivo)
                  archivo.close();
               }
               catch (IOException ex) {
                   Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
               }
        }
    }
    /*
    void imprimirDatos(LinkedList<DoubleMatrix> datos){
        try {
            DoubleMatrix testigo;
            testigo=matrizIncidencia.mmul(datos.get(0));
            //DoubleMatrix patronCeros=DoubleMatrix.zeros(testigo.rows, testigo.columns);
            archivo = new FileWriter(nombreArchSal,true);
            pw = new PrintWriter(archivo);
            pw.print(Thread.currentThread().getName());
            for(int k=0;k<2;k++){
                    for(int i=0;i<datos.get(k).columns;i++){
                        for(int j=0;j<datos.get(k).rows;j++){
                            switch(k){
                                case 0:
                                    if(((int)datos.get(k).get(j,i))>0)
                                        pw.print(" & \\cellcolor{yellow} ");
                                    else
                                        pw.print(" & ");
                                    break;
                                case 1:
                                        if(((int)testigo.get(j,i))!=0)
                                            pw.print(" & \\cellcolor{yellow} ");
                                        else
                                            pw.print(" & ");
                                    break;
                                default:
                                    pw.print(" & ");
                            }
                            pw.print((int)datos.get(k).get(j,i));                            
                        }
                    }
            }   
            pw.print(" \\tabularnewline\n");
            pw.println("\\hline");           
        }
        
        catch (IOException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            try {
               // Nuevamente aprovechamos el finally para 
               // asegurarnos que se cierra el fichero.
               if (null !=archivo)
                  archivo.close();
               }
               catch (IOException ex) {
                   Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
               }
        }
    }
    */
    public void finalizarArchivo(){
        try {
            archivo = new FileWriter(nombreArchSal,true);
            pw = new PrintWriter(archivo);
            pw.println("</table>");
            pw.println("</div>");
            pw.println("</div>");
            pw.println("<p class=\"sig\">Por cualquier duda o sugerencia visite nuestro <a href=\"https://github.com/Andresteve07/FinalConcurrente\">repositorio</a></p>");
            pw.println("</body>");
            pw.println("</html>");
        }
        
        catch (IOException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            try {
               // Nuevamente aprovechamos el finally para 
               // asegurarnos que se cierra el fichero.
               if (null !=archivo)
                  archivo.close();
            } 
            catch (IOException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void metodo(){
        try {
            archivo.close();
        } catch (IOException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
package solucion.sudoku;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class main {

    private static final Logger log = LoggerFactory.getLogger(main.class);
    // guarda el sudoku
    private static int[][] sudoku;
    // guarda los posibles casos
    private static MatrizNumeroSudoku posible;

    /**
     * Busca entre todas las casillas por columna, el posible numero que no se repite, dejando por decarte a este como
     * unica solucion
     * @return
     */
    public static int[] buscarUnicoPosibleColumna(){

        //recorre por fila
        for (int i = 0; i < posible.getTamanio(); i++) {
            //recorre dentro de la fila por columna
            for (int j = 0; j < posible.getTamanio(); j++) {
                //mientras exista un valor posible
                if(posible.getSolucionSudoku(i,j) != null){
                    //recorre todos los valores posibles
                    for (int k = 0; k < 9; k++) {
                        int posibleUnico = posible.getSolucionSudoku(i,j).getNumero(k);
                        boolean semaforo = true;
                        //mientras el posible numero no sea 0
                        if(posibleUnico != 0){
                            //recorre todos los de la columna buscando si existe algun otro cuadro con el mismo posible valor
                            for (int l = 0; l < posible.getTamanio(); l++) {
                                //mientras la casilla sea distinta a null y no sea la misma con la que se compara
                                if(posible.getSolucionSudoku(i,l) != null && posible.getSolucionSudoku(i,l) != posible.getSolucionSudoku(i,j)){
                                    //recorre los posibles datos que tiene la casilla con la que se compara
                                    for (int m = 0; m < 9; m++) {
                                        int comparador = posible.getSolucionSudoku(i,l).getNumero(m);
                                        if(comparador == posibleUnico){
                                            semaforo = false;
                                            break;
                                        }
                                    }
                                }
                                if(!semaforo){
                                    break;
                                }
                            }
                            if(semaforo){
                                int [] unico = {i,j,posibleUnico};
                                return unico;
                            }
                        }
                    }
                }
            }
        }
        int [] ningun = {-1};
        return ningun;
    }

    /**
     * Busca entre todas las casillas por fila, el posible numero que no se repite, dejando por descarte este como solucion
     * @return
     */
    public static int[] buscarUnicoPosibleFila(){

        //recorre por fila
        for (int i = 0; i < posible.getTamanio(); i++) {
            //recorre dentro de la fila por columna
            for (int j = 0; j < posible.getTamanio(); j++) {
                //mientras exista un valor posible
                if(posible.getSolucionSudoku(j,i) != null){
                    //recorre todos los valores posibles
                    for (int k = 0; k < 9; k++) {
                        int posibleUnico = posible.getSolucionSudoku(j,i).getNumero(k);
                        boolean semaforo = true;
                        //mientras el posible numero no sea 0
                        if(posibleUnico != 0){
                            //recorre todos los de la fila buscando si existe algun otro cuadro con el mismo posible valor
                            for (int l = 0; l < posible.getTamanio(); l++) {
                                //mientras la casilla sea distinta a null y no sea la misma con la que se compara
                                if(posible.getSolucionSudoku(l,i) != null && posible.getSolucionSudoku(l,i) != posible.getSolucionSudoku(j,i)){
                                    //recorre los posibles datos que tiene la casilla con la que se compara
                                    for (int m = 0; m < 9; m++) {
                                        int comparador = posible.getSolucionSudoku(l,i).getNumero(m);
                                        if(comparador == posibleUnico){
                                            semaforo = false;
                                            break;
                                        }
                                    }
                                }
                                if(!semaforo){
                                    break;
                                }
                            }
                            if(semaforo){
                                int [] unico = {j,i,posibleUnico};
                                return unico;
                            }
                        }
                    }
                }
            }
        }
        int [] ningun = {-1};
        return ningun;
    }

    /**
     * Busca por toda la matriz de posible alguna casillo donde exista un solo numero como posible, una vez que lo encuentra
     * retorna este junto a su posicion, de caso contrario retorna -1
     * @return
     */
    public static int [] buscarPosibleSolo(){

        for (int i = 0; i < posible.getTamanio(); i++) {
            for (int j = 0; j < posible.getTamanio(); j++) {
                int contadorNumeroPosible = 0;
                int [] numeroSolo = new int [3];
                if(posible.getSolucionSudoku(j,i) != null){
                    for (int k = 0; k < 9; k++) {
                        if(posible.getSolucionSudoku(j,i).getNumero(k) != 0){
                            contadorNumeroPosible++;
                            numeroSolo [0] = j;
                            numeroSolo [1] = i;
                            numeroSolo [2] = posible.getSolucionSudoku(j,i).getNumero(k);
                        }
                    }
                    if(contadorNumeroPosible == 1){
                        return numeroSolo;
                    }
                }
            }
        }
        int [] vacio = {-1};
        return vacio;
    }

    /**
     * Va avanzando por la fila, eliminando todos los numeros que coincidan con el numeroComparar
     * (esto debido a que el numeroComparar es un numero que el archivo Sudoku trae y se descarta como posible solucion)
     * @param numeroComparar
     * @param posFila
     */
    public static void eliminarFila(int numeroComparar, int posFila){

        for (int i = 0; i < posible.getTamanio(); i++) {
            if(posible.getSolucionSudoku(posFila, i) != null){
                int posNumeroBorrar = posible.getSolucionSudoku(posFila,i).compararNumero(numeroComparar);
                if( posNumeroBorrar != -1){
                    posible.getSolucionSudoku(posFila,i).eliminarNumero(posNumeroBorrar);
                }
            }
        }
    }

    /**
     * Va avanzando por la fila, eliminando todos los numeros que coincidan con el numeroComparar
     * (esto debido a que el numeroComparar es un numero que el archivo Sudoku trae y se descarta como posible solucion)
     * @param numeroComparar
     * @param posColumna
     */
    public static void eliminarColuman(int numeroComparar, int posColumna){

        for (int i = 0; i < posible.getTamanio(); i++) {
            if(posible.getSolucionSudoku(i, posColumna) != null) {
                int posNumeroBorrar = posible.getSolucionSudoku(i, posColumna).compararNumero(numeroComparar);
                if (posNumeroBorrar != -1) {
                    posible.getSolucionSudoku(i, posColumna).eliminarNumero(posNumeroBorrar);
                }
            }
        }
    }

    /**
     * Elimina todos los numeros que sean iguales a numeroComparar que se encuentren en el cuadrante
     * @param numeroComparar
     * @param posColumna
     * @param posFila
     */
    public static void eliminarCuadrante(int numeroComparar, int posColumna, int posFila){

        double copiaColumna = posColumna;
        double copiaFila = posFila;

        while(copiaColumna%3 != 0 || copiaFila%3 != 0){

            if(copiaColumna%3 != 0){
                copiaColumna--;
            }
            if(copiaFila%3 != 0){
                copiaFila--;
            }
        }

        for(int i = (int)copiaColumna; i < copiaColumna + 3; i++){
            for (int j = (int) copiaFila; j < copiaFila + 3; j++) {
                if(posible.getSolucionSudoku(j,i) != null) {
                    int posNumeroBorrar = posible.getSolucionSudoku(j,i).compararNumero(numeroComparar);
                    if (posNumeroBorrar != -1) {
                        posible.getSolucionSudoku(j,i).eliminarNumero(posNumeroBorrar);
                    }
                }
            }
        }
    }

    /**
     * intenta leer el sudoku, en caso de no poder return true, en caso de poder se lee y se van a creando el tamaño
     * @return
     * @throws FileNotFoundException
     */
    public static boolean lectura() throws FileNotFoundException {

        try{
            BufferedReader lectura = new BufferedReader(new FileReader("Sudoku.txt"));
            int posColumna = -1;
            String texto;

            while ((texto = lectura.readLine()) != null){

                String [] textosinespacio = texto.split(" ");

                //la primera vez que se lee el archivo este contendra el tamanio del sudoku, por ende aca se inicilizan
                if(posColumna == -1){

                    int tamanio = Integer.parseInt(textosinespacio[0]);
                    sudoku = new int [tamanio][tamanio];
                    posible = new MatrizNumeroSudoku(tamanio);
                }else{
                    //se empieza a rellenar la matriz con los datos del archivo
                    for(int i = 0 ; i < textosinespacio.length; i++){
                        int numero = Integer.parseInt(textosinespacio[i]);
                        sudoku[i][posColumna] = numero;
                        //si en la casilla hay un 0 se crea un NumeroSudoku para poder buscar los posibles valores
                        if(numero == 0){
                            NumeroSudoku numeroSudoku = new NumeroSudoku();
                            posible.setNumeroSudoku(i,posColumna,numeroSudoku);
                        }
                    }

                }
                posColumna++;
            }
            return true;
        }catch (Exception exception){
            return  false;
        }
    }

    /**
     * Soluciona el sudoku que es ingresado por pantalla
     * @param args
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        final StopWatch time = StopWatch.createStarted();
        //lectura del archivo, en caso de que no lo encuentre no entra al if y muestra el mensaje
        if(lectura()){
            int nHilos = 5;
            final ExecutorService executorService = Executors.newFixedThreadPool(nHilos);

           //imprime el sudoku inicial
           for (int i = 0; i < sudoku.length; i++) {
                for (int j = 0; j < sudoku.length; j++) {
                    System.out.print(sudoku[j][i]+" ");
                }
                System.out.println();
            }


            /*
            //Imprime todos los posibles casos (sin eliminar repetidos)
            for(int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++) {
                    if(posible.getSolucionSudoku(j, i) != null){
                        for (int k = 0; k < 9; k++) {
                            System.out.print(posible.getSolucionSudoku(j,i).getNumero(k));
                        }
                    }else{
                        System.out.print("000000000");
                    }
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
            */

            //Recorre la matriz sudoku buscando cuales numeros son diferentes a 0 para poder eliminarlos de los posibles
            for(int i = 0; i < sudoku.length; i++){
                for (int j = 0; j < sudoku.length; j++) {
                    if(sudoku[j][i] != 0){
                        executorService.submit(new eliminar(i, j, sudoku[j][i]));
                    }
                }
            }
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);
            int cantDeMov = 0;

            while (cantDeMov != sudoku.length*sudoku.length){

                final ExecutorService executorService1 = Executors.newFixedThreadPool(nHilos);

                //Busca y va eliminando en todos los posibles numeros el posible que solamente tiene una opcion,
                //debido a que ese es la opcion
                for(int x = 0; x < posible.getTamanio()*posible.getTamanio();x++){
                    int [] numeroEliminar = buscarPosibleSolo();
                    if(numeroEliminar[0] != -1){
                        posible.setSolucionSudoku(numeroEliminar[0], numeroEliminar[1]);
                        sudoku[numeroEliminar[0]][numeroEliminar[1]]= numeroEliminar[2];
                        executorService1.submit(new eliminar(numeroEliminar[1], numeroEliminar[0], numeroEliminar[2]));
                    }
                }
                executorService1.shutdown();
                executorService1.awaitTermination(1, TimeUnit.HOURS);

                int [] eliminarFila = buscarUnicoPosibleFila();
                //Busca al unico que puede ser en toda la fila, si es distinto a -1 significa que existe uno para eliminar
                if(eliminarFila[0] != -1){
                    final ExecutorService executorService2 = Executors.newFixedThreadPool(nHilos);
                    posible.setSolucionSudoku(eliminarFila[0], eliminarFila[1]);
                    sudoku[eliminarFila[0]][eliminarFila[1]]= eliminarFila[2];
                    executorService2.submit(new eliminar(eliminarFila[1], eliminarFila[0], eliminarFila[2]));
                    executorService2.shutdown();
                    executorService2.awaitTermination(1, TimeUnit.HOURS);
                }

                int [] eliminarColumna = buscarUnicoPosibleColumna();
                //Busca al unico que puede ser en toda la columna, si es distinto a -1 significa que existe uno para eliminar
                if(eliminarColumna[0] != -1){
                    final ExecutorService executorService3 = Executors.newFixedThreadPool(nHilos);
                    posible.setSolucionSudoku(eliminarColumna[0], eliminarColumna[1]);
                    sudoku[eliminarColumna[0]][eliminarColumna[1]]= eliminarColumna[2];
                    executorService3.submit(new eliminar(eliminarColumna[1], eliminarColumna[0], eliminarColumna[2]));
                    executorService3.shutdown();
                    executorService3.awaitTermination(1, TimeUnit.HOURS);
                }

                cantDeMov++;
            }
        }else{
            log.debug("Sudoku no encontrado");
        }
        log.debug("demoro {} tiempo en solucionar el sudoku",time);

        //imprime el sudoku final
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                System.out.print(sudoku[j][i]+" ");
            }
            System.out.println();
        }
    }

    /**
     * Runnable, ejecuta el eliminar el numero seleccionado anteriormente, es eliminado por cuadrante, fila y columna
     */
    private static class eliminar implements Runnable{

        private int posColumna;
        private int posFila;
        private int numeroComparar;

        eliminar(int posColumna, int posFila, int numeroComparar){
            this.posColumna = posColumna;
            this.posFila = posFila;
            this.numeroComparar = numeroComparar;
        }

        @Override
        public void run() {
            eliminarColuman(numeroComparar,posColumna);
            eliminarFila(numeroComparar,posFila);
            eliminarCuadrante(numeroComparar,posColumna,posFila);
        }
    }
}
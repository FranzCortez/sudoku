package solucion.sudoku;

public class NumeroSudoku {

    private int[] posibleNumero;

    //se encarga de tener un vector con todos los posibles casos que puede estar en la casilla
    public NumeroSudoku() {
        this.posibleNumero = new int [9];
        rellenarLista();
    }

    public void rellenarLista(){
        for(int i = 0; i < 9; i++){
            this.posibleNumero[i] = i + 1;
        }
    }

    public int getNumero(int index){
        return posibleNumero[index];
    }

    public int compararNumero(int numeroComparar){

        for (int i = 0; i < posibleNumero.length; i++) {
            if(posibleNumero[i] == numeroComparar){
                return i;
            }
        }
        return -1;
    }

    public void eliminarNumero(int posEliminar){

        posibleNumero[posEliminar] = 0;
    }
}

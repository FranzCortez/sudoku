package solucion.sudoku;

public class MatrizNumeroSudoku {

    private int tamanio;
    private NumeroSudoku [][] solucionSudoku;

    //se encarga de tener una matriz de NumeroSudoku donde ira guardando en cada casilla necesaria un NumeroSudoku
    public MatrizNumeroSudoku(int tamanio) {
        this.solucionSudoku = new NumeroSudoku[tamanio][tamanio];
        this.tamanio = tamanio;
    }

    public void setNumeroSudoku(int posFila, int posColumna, NumeroSudoku numeroSudoku) {
        this.solucionSudoku [posFila][posColumna] = numeroSudoku;
    }

    public NumeroSudoku getSolucionSudoku(int posFila, int posColumna) {
        return solucionSudoku[posFila][posColumna];
    }
    public int getTamanio() {
        return tamanio;
    }

    public void setSolucionSudoku(int posFila, int posColumna){
        this.solucionSudoku[posFila][posColumna] = null;
    }
}

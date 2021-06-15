package italo.xadrez.algoritmo.computador.to;

import java.util.LinkedList;
import java.util.List;

public class Jogada {
          
    private int i1;
    private int j1;
    private int i2;
    private int j2;
    
    private int peso = 0;
    
    private Jogada parente = null;
    private final List<Jogada> jogadas = new LinkedList();
       
    public void addJogada( Jogada jogada ) {
        jogada.setParente( this );
        jogadas.add( jogada );
    }
    
    public Jogada raiz() {
        if ( parente == null )
            return this;
        return parente.raiz();
    }

    public List<Jogada> getJogadas() {
        return jogadas;
    }

    public Jogada getParente() {
        return parente;
    }

    public void setParente(Jogada parente) {
        this.parente = parente;
    }    

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public int getJ1() {
        return j1;
    }

    public void setJ1(int j1) {
        this.j1 = j1;
    }

    public int getI2() {
        return i2;
    }

    public void setI2(int i2) {
        this.i2 = i2;
    }

    public int getJ2() {
        return j2;
    }

    public void setJ2(int j2) {
        this.j2 = j2;
    }
    
}

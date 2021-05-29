package italo.xadrez.controller;

import italo.xadrez.Sistema;
import italo.xadrez.nucleo.move.MoveuListener;

public class MoveuController implements MoveuListener {
    
    private final Sistema sistema;
    
    public MoveuController( Sistema sistema ) {
        this.sistema = sistema;
    } 

    @Override
    public void moveu() {
        sistema.repaint();
    }
    
}

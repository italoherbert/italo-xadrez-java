package italo.xadrez.controller;

import italo.xadrez.Sistema;
import italo.xadrez.gui.janela.JanelaGUIListener;

public class JanelaController implements JanelaGUIListener {

    private final Sistema sistema;
    
    public JanelaController( Sistema sistema ) {
        this.sistema = sistema;
    }
    
    @Override
    public void fechandoJanela() {
        sistema.getJogo().abandonarPartida();
    }        

}

package italo.xadrez.controller;

import italo.xadrez.Sistema;
import italo.xadrez.gui.desenho.DesenhoGUIListener;
import italo.xadrez.nucleo.Jogo;

public class JogoDesenhoController implements DesenhoGUIListener {

    private final Sistema sistema;
        
    public JogoDesenhoController( Sistema sistema ) {
        this.sistema = sistema;
    }
    
    @Override
    public void mouseClicado( int mouseX, int mouseY ) {
        Jogo jogo = sistema.getJogo();
        
        if ( jogo.isFim() ) {
            sistema.getJogoCtrl().paraTelaMenu();
            return;
        }        
        
        if ( jogo.isUsuarioVersusComputador() ) {
            sistema.getJogador().joga( mouseX, mouseY );            
        }
    }
    
}

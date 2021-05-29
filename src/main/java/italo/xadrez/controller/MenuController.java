package italo.xadrez.controller;

import italo.xadrez.Sistema;
import italo.xadrez.algoritmo.ComputadorAlgoritmo;
import italo.xadrez.gui.menu.MenuGUIListener;
import italo.xadrez.gui.menu.MenuGUITO;
import italo.xadrez.media.AudioManager;

public class MenuController implements MenuGUIListener {

    private final Sistema sistema;

    public MenuController(Sistema sistema) {
        this.sistema = sistema;
    }
    
    @Override
    public void iniciarBTAcionado( MenuGUITO guiTO ) {
        if( guiTO.getNivelJogadorComp2() == MenuGUITO.FACIL ) {
            sistema.getComputador2().setNivel( ComputadorAlgoritmo.FACIL );
        } else if( guiTO.getNivelJogadorComp2() == MenuGUITO.MEDIANO ) {
            sistema.getComputador2().setNivel( ComputadorAlgoritmo.MEDIANO );
        } else {
            sistema.getComputador2().setNivel( ComputadorAlgoritmo.DIFICIL );
        } 

        sistema.getJogo().setUsuarioVersusComputador( guiTO.isJogador1Humano() );
        sistema.getJogo().reinicia();
        
        sistema.getMediaManager().getAudioManager().stop( AudioManager.ABERTURA_FUNDO ); 
        sistema.getMediaManager().getAudioManager().play( AudioManager.JOGO_FUNDO, -1 );

        sistema.getGUI().getJanelaGUI().showJogoPNL();
        
        if ( !guiTO.isJogador1Humano() ) {            
            if( guiTO.getNivelJogadorComp1() == MenuGUITO.FACIL ) {
                sistema.getComputador1().setNivel( ComputadorAlgoritmo.FACIL );
            } else if( guiTO.getNivelJogadorComp1() == MenuGUITO.MEDIANO ) {
                sistema.getComputador1().setNivel( ComputadorAlgoritmo.MEDIANO );
            } else {
                sistema.getComputador1().setNivel( ComputadorAlgoritmo.DIFICIL );
            }
            
            sistema.startCompVersusComp();
        }
    }
    
}

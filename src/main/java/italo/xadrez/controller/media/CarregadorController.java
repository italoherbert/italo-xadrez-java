package italo.xadrez.controller.media;

import italo.xadrez.Sistema;
import italo.xadrez.media.AudioManager;
import italo.xadrez.media.MediaCarregadorListener;

public class CarregadorController implements MediaCarregadorListener {
    
    private final Sistema sistema;

    public CarregadorController(Sistema sistema) {
        this.sistema = sistema;
    }

    @Override
    public void carregouImagemAbertura() {
        sistema.getGUI().getJanelaGUI().getCarregandoPNL().repaint();
    }

    @Override
    public void carregou() {
        sistema.getJogo().inicializa();
                
        try {
            Thread.sleep( 1000 ); 
        } catch( InterruptedException e ) {
            
        }
                    
        sistema.getJogoCtrl().paraTelaMenu();
    }
    
}

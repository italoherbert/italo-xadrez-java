package italo.xadrez.controller;

import italo.xadrez.Sistema;
import italo.xadrez.media.AudioManager;
import italo.xadrez.nucleo.JogoListener;

public class JogoController implements JogoListener {

    private final Sistema sistema;

    public JogoController(Sistema sistema) {
        this.sistema = sistema;
    }

    @Override
    public void compAlgoritmoFinalizou() {
        
    }
        
    @Override
    public void pretasMoveu() {
        sistema.getMediaManager().getAudioManager().play( AudioManager.PRETAS_MOVEU );
    }

    @Override
    public void brancasMoveu() {
        sistema.getMediaManager().getAudioManager().play( AudioManager.BRANCAS_MOVEU );        
    }

    @Override
    public void capturou() {
        sistema.getMediaManager().getAudioManager().play( AudioManager.CAPTURA );         
    }

    @Override
    public void xeque() {
        sistema.getMediaManager().getAudioManager().play( AudioManager.XEQUE ); 
    }

    @Override
    public void pretasVenceram() {
        sistema.getMediaManager().getAudioManager().play( AudioManager.PRETAS_VENCERAM );         
    }

    @Override
    public void brancasVenceram() {
        sistema.getMediaManager().getAudioManager().play( AudioManager.BRANCAS_VENCERAM );         
    }

    @Override
    public void empatou() {
        sistema.getMediaManager().getAudioManager().play( AudioManager.EMPATOU );         
    }
    
}
